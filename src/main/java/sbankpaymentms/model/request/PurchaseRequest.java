package sbankpaymentms.model.request;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {

    @DecimalMin(value = "1.0", message = "The amount must be greater than or equal to 1.0")
    private BigDecimal amount;

}
