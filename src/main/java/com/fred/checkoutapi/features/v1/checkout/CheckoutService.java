package com.fred.checkoutapi.features.v1.checkout;

import com.fred.checkoutapi.client.PaymentClient;
import com.fred.checkoutapi.model.entity.Checkout;
import com.fred.checkoutapi.model.enums.CheckoutStatus;
import com.fred.checkoutapi.model.request.CheckoutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CheckoutRepository checkoutRepository;

    private final PaymentClient paymentClient;

    public Optional<Checkout> findCheckoutById(final UUID checkoutId) {
        return checkoutRepository.findCheckoutById(checkoutId);
    }

    public Checkout createOrder(CheckoutRequest request) {
        Checkout checkout = checkoutRepository.save(Checkout.builder()
                .customerEmail(request.getCustomerEmail())
                .customerName(request.getCustomerName())
                .deliveryAddress(request.getDeliveryAddress())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .status(CheckoutStatus.PENDING)
                .build());

        paymentClient.processPayment(checkout.getId())
                .ifPresent(paymentResponse -> {
                    if (!paymentResponse.isSuccess()) {
                        checkout.setStatus(CheckoutStatus.CANCELED);
                        checkoutRepository.save(checkout);
                    }
                });
        return checkout;
    }

}
