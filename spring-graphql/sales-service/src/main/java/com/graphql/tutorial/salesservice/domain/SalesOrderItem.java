package com.graphql.tutorial.salesservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sales_order_items")
public class SalesOrderItem {

    @Id
    @GeneratedValue
    private Long id;

    private int quantity;

    private Long modelId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SalesOrderItem{");
        sb.append("id=").append(id);
        sb.append(", quantity=").append(quantity);
        sb.append(", modelId=").append(modelId);
        sb.append('}');
        return sb.toString();
    }
}
