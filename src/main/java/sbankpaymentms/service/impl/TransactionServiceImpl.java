package sbankpaymentms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sbankpaymentms.client.impl.CustomerClientImpl;
import sbankpaymentms.dao.entity.Transaction;
import sbankpaymentms.dao.repository.TransactionRepository;
import sbankpaymentms.enums.TransactionStatus;
import sbankpaymentms.enums.TransactionType;
import sbankpaymentms.exception.NotEnoughBalanceException;
import sbankpaymentms.exception.RefundAmountConflictException;
import sbankpaymentms.exception.TransactionExpiredException;
import sbankpaymentms.exception.TransactionIsNotRefundableException;
import sbankpaymentms.exception.TransactionNotFoundException;
import sbankpaymentms.kafka.payload.BalanceOperationPayload;
import sbankpaymentms.kafka.payload.BalanceOperationResponsePayload;
import sbankpaymentms.kafka.producer.Producer;
import sbankpaymentms.model.UserClaims;
import sbankpaymentms.model.request.PurchaseRequest;
import sbankpaymentms.model.request.RefundRequest;
import sbankpaymentms.model.request.TopUpRequest;
import sbankpaymentms.model.response.CustomerResponse;
import sbankpaymentms.model.response.TransactionResponse;
import sbankpaymentms.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static sbankpaymentms.enums.TransactionStatus.SUCCESS;
import static sbankpaymentms.enums.TransactionType.PURCHASE;
import static sbankpaymentms.enums.TransactionType.REFUND;
import static sbankpaymentms.enums.TransactionType.TOP_UP;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Value("${kafka.topics.balance-response}")
    private String balanceResponseTopic;

    @Value("${kafka.topics.update-balance}")
    private String updateBalanceTopic;

    private final TransactionRepository transactionRepository;
    private final CustomerClientImpl customerClient;
    private final Producer producer;

    @Override
    public TransactionResponse createTopUp(TopUpRequest request, UserClaims userClaims) {
        customerClient.getCustomer(userClaims.getCif());
        Transaction transaction = buildTransaction(request.getAmount(), TOP_UP, null, userClaims);
        UUID transactionId = transactionRepository.save(transaction).getId();
        return TransactionResponse.builder()
                .transactionId(transactionId)
                .build();
    }

    @Override
    public TransactionResponse createRefund(RefundRequest request, UserClaims userClaims) {
        Transaction transaction = transactionRepository
                .findByIdAndCif(request.getRefundTransactionId(), userClaims.getCif())
                .orElseThrow(TransactionNotFoundException::new);
        checkIsRefundable(transaction);
        if (hasEnoughAmount(transaction.getAmount(), request.getAmount())) {
            Transaction refundTransaction = buildTransaction(request.getAmount(), REFUND,
                    request.getRefundTransactionId(), userClaims);
            UUID transactionId = transactionRepository.save(refundTransaction).getId();
            return TransactionResponse.builder()
                    .transactionId(transactionId)
                    .build();
        }
        throw new RefundAmountConflictException();
    }

    @Override
    public TransactionResponse createPurchase(PurchaseRequest request, UserClaims userClaims) {
        CustomerResponse customer = customerClient.getCustomer(userClaims.getCif());
        if (hasEnoughAmount(customer.getBalance(), request.getAmount())) {
            Transaction transaction = buildTransaction(request.getAmount(), PURCHASE, null, userClaims);
            UUID transactionId = transactionRepository.save(transaction).getId();
            return TransactionResponse.builder()
                    .transactionId(transactionId)
                    .build();
        }
        throw new NotEnoughBalanceException();
    }

    @Override
    public void approve(UUID transactionId, UserClaims userClaims) {
        transactionRepository.findById(transactionId)
                .ifPresentOrElse(transaction -> {
                            if (transaction.getExpiredAt().isBefore(LocalDateTime.now())) {
                                throw new TransactionExpiredException();
                            }
                            BalanceOperationPayload balanceOperationPayload = buildBalanceOperationPayload(transaction);
                            producer.produce(updateBalanceTopic, balanceOperationPayload);
                        },
                        () -> {
                            throw new TransactionNotFoundException();
                        }
                );

    }

    @Override
    public void updateTransaction(BalanceOperationResponsePayload payload) {
        transactionRepository.findById(payload.getTransactionId())
                .ifPresent(transaction -> {
                    transaction.setStatus(payload.getStatus());
                    transactionRepository.save(transaction);
                });
    }

    private void checkIsRefundable(Transaction transaction) {
        if (!transaction.getType().equals(PURCHASE) && !transaction.getStatus().equals(SUCCESS)) {
            throw new TransactionIsNotRefundableException();
        }
    }

    private boolean hasEnoughAmount(BigDecimal balance, BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    private Transaction buildTransaction(BigDecimal amount, TransactionType type,
                                         UUID refundTransactionId, UserClaims userClaims) {
        return Transaction.builder()
                .amount(amount)
                .status(TransactionStatus.CREATED)
                .type(type)
                .refundTransactionId(refundTransactionId)
                .cif(userClaims.getCif())
                .expiredAt(LocalDateTime.now().plusMinutes(10))
                .build();
    }

    private BalanceOperationPayload buildBalanceOperationPayload(Transaction transaction) {
        return BalanceOperationPayload.builder()
                .amount(transaction.getAmount())
                .transactionId(transaction.getId())
                .refundTransactionId(transaction.getRefundTransactionId())
                .cif(transaction.getCif())
                .type(transaction.getType())
                .responseTopic(balanceResponseTopic)
                .build();
    }

}
