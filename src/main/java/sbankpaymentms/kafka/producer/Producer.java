package sbankpaymentms.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class Producer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public <T> void produce(String topic, T payload) {
        kafkaTemplate.send(topic, payload);
    }

}

