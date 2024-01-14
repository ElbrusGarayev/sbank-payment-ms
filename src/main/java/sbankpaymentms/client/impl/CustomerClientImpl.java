package sbankpaymentms.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sbankpaymentms.client.CustomerClient;
import sbankpaymentms.exception.CustomerNotFoundException;
import sbankpaymentms.model.response.CustomerResponse;

@Service
@RequiredArgsConstructor
public class CustomerClientImpl {

    private final CustomerClient customerClient;

    public CustomerResponse getCustomer(String cif) {
        try {
            return customerClient.getCustomer(cif).getBody();
        } catch (Exception ex) {
            throw new CustomerNotFoundException();
        }
    }

}
