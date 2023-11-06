package com.graphql.tutorial.salesservice.repository;

import com.graphql.tutorial.salesservice.domain.SalesOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Long>, JpaSpecificationExecutor<SalesOrderItem> {

    /*@Query(nativeQuery = true, value = "SELECT COALESCE( SUM(s.quantity), 0) FROM sales_order_items s")
    double totalSalesQuantity();

    @Query(nativeQuery = true, value = """
            SELECT COALESCE( SUM(s.quantity), 0 )
              FROM sales_order_items s
             WHERE s.model_id = :modelUuid
              """)
    double modelSalesQuantity(@Param("modelUuid") UUID modelUuid);*/

}
