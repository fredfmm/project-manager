package com.fred.checkoutapi.features.v1.checkout;
import com.fred.checkoutapi.model.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    @Query("""
                FROM Checkout c
                WHERE
                c.id = :checkoutId
                """)
    Optional<Checkout> findCheckoutById(@Param("checkoutId") UUID checkoutId);


}
