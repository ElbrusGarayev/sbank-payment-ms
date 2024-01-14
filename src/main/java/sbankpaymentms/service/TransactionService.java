package sbankpaymentms.service;

import sbankpaymentms.kafka.payload.BalanceOperationResponsePayload;
import sbankpaymentms.model.UserClaims;
import sbankpaymentms.model.request.PurchaseRequest;
import sbankpaymentms.model.request.RefundRequest;
import sbankpaymentms.model.request.TopUpRequest;
import sbankpaymentms.model.response.TransactionResponse;

import java.util.UUID;

public interface TransactionService {

    TransactionResponse createTopUp(TopUpRequest request, UserClaims userClaims);

    TransactionResponse createRefund(RefundRequest request, UserClaims userClaims);

    TransactionResponse createPurchase(PurchaseRequest request, UserClaims userClaims);

    void approve(UUID transactionId, UserClaims userClaims);

    void updateTransaction(BalanceOperationResponsePayload payload);

}
