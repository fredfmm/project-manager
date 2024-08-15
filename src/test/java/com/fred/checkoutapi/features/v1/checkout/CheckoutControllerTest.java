package com.fred.checkoutapi.features.v1.checkout;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fred.checkoutapi.model.entity.Checkout;
import com.fred.checkoutapi.model.request.CheckoutRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.UUID;

import static com.fred.checkoutapi.model.enums.CheckoutStatus.PENDING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CheckoutController.class)
class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckoutService checkoutService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBalanceByProductSuccessTest() throws Exception {
        UUID checkoutId = UUID.randomUUID();
        Checkout checkout = Checkout.builder()
                .id(checkoutId)
                .customerName("teste")
                .deliveryAddress("teste")
                .customerEmail("teste")
                .status(PENDING)
                .quantity(2)
                .productId(UUID.randomUUID())
                .build();

        when(checkoutService.findCheckoutById(checkoutId)).thenReturn(Optional.of(checkout));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/checkout/" + checkoutId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        verify(checkoutService).findCheckoutById(checkoutId);
    }

    @Test
    void createOrderSuccessTest() throws Exception {
        Checkout request = Checkout.builder()
                .id(UUID.randomUUID())
                .customerName("teste")
                .deliveryAddress("teste")
                .customerEmail("teste")
                .status(PENDING)
                .quantity(2)
                .productId(UUID.randomUUID())
                .build();

        Checkout order = Checkout.builder()
                .id(UUID.randomUUID())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build();

        when(checkoutService.createOrder(any(CheckoutRequest.class))).thenReturn(order);

        String requestJson = objectMapper.writeValueAsString(request);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/checkout/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        resultActions.andExpect(status().isOk());

        verify(checkoutService).createOrder(any(CheckoutRequest.class));
    }

}