package sbankpaymentms.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequest {

    @DecimalMin(value = "1.0", message = "The amount must be greater than or equal to 1.0")
    private BigDecimal amount;

    @NotBlank
    private UUID refundTransactionId;

}
