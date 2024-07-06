package com.jeevanraksha.appointment_service.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                System.out.println("Attributes from appointment: " + attributes);
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();
                    String token = request.getHeader("Authorization");
                    System.out.println("Token from appointment: " + token);

                    // Add the token to the Authorization header if it exists
                    if (token != null && !token.isEmpty()) {
                        requestTemplate.header("Authorization", token);
                    }
                }
            }
        };
    }
}
