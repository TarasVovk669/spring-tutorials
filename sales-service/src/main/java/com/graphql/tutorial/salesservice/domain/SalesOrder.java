package com.graphql.tutorial.salesservice.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales_orders")
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String orderNumber;

    @CreationTimestamp
    private ZonedDateTime createdDateTime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "salesOrder", cascade = CascadeType.ALL)
    private Finance finance;

    @JoinColumn(name = "sales_order_id")
    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<SalesOrderItem> salesOrderItems = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public ZonedDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(ZonedDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Finance getFinance() {
        return finance;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    public List<SalesOrderItem> getSalesOrderItems() {
        return salesOrderItems;
    }

    public void setSalesOrderItems(List<SalesOrderItem> salesOrderItems) {
        this.salesOrderItems = salesOrderItems;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SalesOrder{");
        sb.append("id=").append(id);
        sb.append(", orderNumber='").append(orderNumber).append('\'');
        sb.append(", createdDateTime=").append(createdDateTime);
        sb.append(", customer=").append(customer);
        sb.append(", finance=").append(finance);
        sb.append(", salesOrderItems=").append(salesOrderItems);
        sb.append('}');
        return sb.toString();
    }


    public static final class SalesOrderBuilder {
        private Long id;
        private String orderNumber;
        private ZonedDateTime createdDateTime;
        private Customer customer;
        private Finance finance;
        private List<SalesOrderItem> salesOrderItems;

        private SalesOrderBuilder() {
        }

        public static SalesOrderBuilder aSalesOrder() {
            return new SalesOrderBuilder();
        }

        public SalesOrderBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SalesOrderBuilder orderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public SalesOrderBuilder createdDateTime(ZonedDateTime createdDateTime) {
            this.createdDateTime = createdDateTime;
            return this;
        }

        public SalesOrderBuilder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public SalesOrderBuilder finance(Finance finance) {
            this.finance = finance;
            return this;
        }

        public SalesOrderBuilder salesOrderItems(List<SalesOrderItem> salesOrderItems) {
            this.salesOrderItems = salesOrderItems;
            return this;
        }

        public SalesOrder build() {
            SalesOrder salesOrder = new SalesOrder();
            salesOrder.setId(id);
            salesOrder.setOrderNumber(orderNumber);
            salesOrder.setCreatedDateTime(createdDateTime);
            salesOrder.setCustomer(customer);
            salesOrder.setFinance(finance);
            salesOrder.setSalesOrderItems(salesOrderItems);
            return salesOrder;
        }
    }
}
