package sbankpaymentms.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.kafka.support.JacksonUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectMapperUtils {

    public static ObjectMapper getEnhancedObjectMapperWithCamelCase() {
        return JacksonUtils.enhancedObjectMapper();
    }

}

