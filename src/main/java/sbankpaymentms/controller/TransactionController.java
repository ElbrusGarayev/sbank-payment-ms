package sbankpaymentms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sbankpaymentms.model.UserClaims;
import sbankpaymentms.model.request.PurchaseRequest;
import sbankpaymentms.model.request.RefundRequest;
import sbankpaymentms.model.request.TopUpRequest;
import sbankpaymentms.model.response.TransactionResponse;
import sbankpaymentms.service.TransactionService;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/top-up")
    public ResponseEntity<TransactionResponse> createTopUp(@RequestBody TopUpRequest request,
                                                                 UserClaims userClaims) {
        TransactionResponse transactionResponse = transactionService.createTopUp(request, userClaims);
        return ResponseEntity.ok(transactionResponse);
    }

    @PostMapping("/refund")
    public ResponseEntity<TransactionResponse> createRefund(@RequestBody RefundRequest request,
                                                           UserClaims userClaims) {
        TransactionResponse transactionResponse = transactionService.createRefund(request, userClaims);
        return ResponseEntity.ok(transactionResponse);
    }

    @PostMapping("/purchase")
    public ResponseEntity<TransactionResponse> createPurchase(@RequestBody PurchaseRequest request,
                                                           UserClaims userClaims) {
        TransactionResponse transactionResponse = transactionService.createPurchase(request, userClaims);
        return ResponseEntity.ok(transactionResponse);
    }

    @PutMapping("/{transactionId}")
    @ResponseStatus(NO_CONTENT)
    public void approveTransaction(@PathVariable UUID transactionId, UserClaims userClaims) {
        transactionService.approve(transactionId, userClaims);
    }

}
