package com.fred.checkoutapi.model.request;


import lombok.*;

import java.util.UUID;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutRequest {

    private UUID productId;
    private Integer quantity;
    private String customerName;
    private String customerEmail;
    private String deliveryAddress;

}

