package sbankpaymentms.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import sbankpaymentms.kafka.payload.BalanceOperationResponsePayload;
import sbankpaymentms.service.TransactionService;

import static sbankpaymentms.util.ObjectMapperUtils.getEnhancedObjectMapperWithCamelCase;

@Slf4j
@Component
@RequiredArgsConstructor
public class Consumer {

    private final TransactionService transactionService;
    private final ObjectMapper objectMapperWithCamelCase = getEnhancedObjectMapperWithCamelCase();

    @KafkaListener(topics = "${kafka.topics.balance-response}", groupId = "${kafka.configs.group-id}",
            containerFactory = "paymentConcurrentKafkaListener")
    public void consume(@Payload String message) throws JsonProcessingException {
        log.info("consumed update transaction message: {}", message);
        try {
            var payload = objectMapperWithCamelCase.readValue(message, BalanceOperationResponsePayload.class);
            transactionService.updateTransaction(payload);
        } catch (Exception ex) {
            log.error("Exception occurred while consuming update transaction message", ex);
            throw ex;
        }
    }

}
