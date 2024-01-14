package sbankpaymentms.kafka.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sbankpaymentms.enums.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceOperationPayload {

    private UUID transactionId;
    private UUID refundTransactionId;
    private String cif;
    private BigDecimal amount;
    private TransactionType type;
    private String responseTopic;

}
