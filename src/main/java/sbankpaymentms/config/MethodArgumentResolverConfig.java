package sbankpaymentms.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sbankpaymentms.util.UserClaimsResolver;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MethodArgumentResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userClaimsResolver());
    }

    @Bean
    public UserClaimsResolver userClaimsResolver() {
        return new UserClaimsResolver();
    }

}
