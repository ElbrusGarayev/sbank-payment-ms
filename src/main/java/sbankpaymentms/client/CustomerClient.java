package sbankpaymentms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sbankpaymentms.model.response.CustomerResponse;

@FeignClient(value = "customer-ms", url = "${feign.client.customer-ms.url}")
public interface CustomerClient {

    @GetMapping("/customers/{cif}")
    ResponseEntity<CustomerResponse> getCustomer(@PathVariable String cif);

}
