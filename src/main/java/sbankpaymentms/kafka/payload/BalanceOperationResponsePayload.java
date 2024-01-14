package sbankpaymentms.kafka.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sbankpaymentms.enums.TransactionStatus;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceOperationResponsePayload {

    private TransactionStatus status;
    private UUID transactionId;

}
