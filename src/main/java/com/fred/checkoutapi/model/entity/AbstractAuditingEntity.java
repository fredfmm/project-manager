package com.fred.checkoutapi.model.entity;

import com.fred.checkoutapi.model.enums.CheckoutStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractAuditingEntity {

	@CreatedDate
	@Column(name="created_at", nullable = false)
	private OffsetDateTime createdAt = OffsetDateTime.now();

	@LastModifiedDate
	@Column(name="updated_at", nullable = false)
	private OffsetDateTime updatedAt = OffsetDateTime.now();

}
