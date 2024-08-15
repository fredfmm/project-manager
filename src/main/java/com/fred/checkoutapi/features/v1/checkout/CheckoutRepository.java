package com.fred.checkoutapi.features.v1.checkout;

import com.fred.checkoutapi.model.entity.Order;
import com.fred.checkoutapi.model.enums.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Order, Long> {

    @Query("from Order where assignment = :assignment")
    List<Order> findAllEmployeesByAssignment(@Param("assignment") Assignment assignment);

}
