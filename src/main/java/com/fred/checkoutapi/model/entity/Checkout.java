package com.fred.checkoutapi.model.entity;

import com.fred.checkoutapi.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private OrderStatus status;

}
