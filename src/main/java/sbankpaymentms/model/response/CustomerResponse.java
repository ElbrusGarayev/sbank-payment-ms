package sbankpaymentms.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private String cif;
    private String name;
    private String surname;
    private String gsmNumber;
    private BigDecimal balance;
    private LocalDate birthDate;

}
