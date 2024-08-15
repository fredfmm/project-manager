package com.fred.checkoutapi.model.request;


import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private Long productId;
    private Integer quantity;
    private String customerName;
    private String customerEmail;
    private String deliveryAddress;

    // Outros campos relevantes, se necessário
}

