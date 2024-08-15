package com.fred.checkoutapi.features.v1.checkout;

import com.fred.checkoutapi.model.entity.Checkout;
import com.fred.checkoutapi.model.request.CheckoutRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/checkout")
@AllArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @GetMapping(value = "/{checkoutId}")
    public ResponseEntity<Checkout> getBalanceByProduct(
            @PathVariable UUID checkoutId) {
        return checkoutService.findCheckoutById(checkoutId)
                .map(balance -> ResponseEntity.ok().body(balance))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/createOrder")
    public ResponseEntity<Checkout> createOrder(@RequestBody CheckoutRequest request) {
        Checkout order = checkoutService.createOrder(request);
        return ResponseEntity.ok(order);
    }
}