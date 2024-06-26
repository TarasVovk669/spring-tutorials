package com.reactive.order.repository;

import com.reactive.order.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long> {

    List<PurchaseOrder> getAllByUserId(Long userId);
}
