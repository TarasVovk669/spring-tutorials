package com.reactive.webflux.demo.sec01.repository;

import com.reactive.webflux.demo.sec01.domain.Customer;
import com.reactive.webflux.demo.sec01.domain.CustomerOrder;
import com.reactive.webflux.demo.sec01.domain.Product;
import com.reactive.webflux.demo.sec01.dto.OrderDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomerOrderRepository extends ReactiveCrudRepository<CustomerOrder,Long> {

    Flux<Customer> findBy(Pageable pageable);

    @Query("""
            SELECT
                p.*
            FROM
                customer c
            INNER JOIN customer_order co ON c.id = co.customer_id
            INNER JOIN product p ON co.product_id = p.id
            WHERE
                c.name = :name
            """)
    Flux<Product> getProductsOrderedByCustomer(String name);

    @Query("""
            SELECT
                co.order_id,
                c.name AS customer_name,
                p.description AS product_name,
                co.amount,
                co.order_date
            FROM
                customer c
            INNER JOIN customer_order co ON c.id = co.customer_id
            INNER JOIN product p ON p.id = co.product_id
            WHERE
                p.description = :description
            ORDER BY co.amount DESC
            """)
    Flux<OrderDetails> getOrderDetails(String description);
}
