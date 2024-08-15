package com.fred.checkoutapi.features.v1.checkout

import com.fred.checkoutapi.client.PaymentClient
import com.fred.checkoutapi.model.request.CheckoutRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("it")
class CheckoutServiceIT extends Specification {

    @Autowired
    CheckoutService checkoutService

    @Autowired
    PaymentClient paymentClient

    def "should find checkout by ID"() {
        given: "a checkout with a specific ID"
        def request = CheckoutRequest.builder()
                .customerEmail("customer@example.com")
                .customerName("John Doe")
                .deliveryAddress("123 Main St")
                .productId(UUID.randomUUID())
                .quantity(1)
                .build()

        and: "the checkout is stored in the repository"
        def checkout = checkoutService.createOrder(request)

        when: "finding checkout by ID"
        def result = checkoutService.findCheckoutById(checkout.getId())

        then: "the checkout should be returned"
        result.isPresent()
    }

}
