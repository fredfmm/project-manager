package com.fred.checkoutapi.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fred.checkoutapi.model.response.request.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Component
public class PaymentClient {

    private final RestTemplate restTemplate;

    public PaymentClient(@Value("${payment.gateway.url}") String authorizationUrl, ObjectMapper objectMapper) {
        this.restTemplate = new RestTemplateBuilder()
                .rootUri(authorizationUrl)
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(3))
                .messageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .additionalInterceptors((request, body, execution) -> {
                    HttpHeaders httpHeaders = request.getHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    return execution.execute(request, body);
                })
                .build();
    }

    public Optional<PaymentResponse> processPayment(final UUID orderId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            return Optional.ofNullable(restTemplate.exchange(
                    "/payment?orderId=" + orderId,
                    HttpMethod.POST,
                    requestEntity,
                    PaymentResponse.class).getBody());
        } catch (HttpStatusCodeException e) {
            return Optional.empty();
        }
    }
}