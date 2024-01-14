package sbankpaymentms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SbankPaymentMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbankPaymentMsApplication.class, args);
	}

}
