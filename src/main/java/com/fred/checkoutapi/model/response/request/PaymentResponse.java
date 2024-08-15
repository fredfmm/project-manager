package com.fred.checkoutapi.model.response.request;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private boolean success;
    private String message;
}

