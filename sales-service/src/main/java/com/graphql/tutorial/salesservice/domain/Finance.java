package com.graphql.tutorial.salesservice.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "finances")
public class Finance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal baseAmount;

    private BigDecimal taxAmount;

    private BigDecimal discountAmount;

    private boolean isLoan;

    @OneToOne
    @JoinColumn(name = "sales_order_id")
    private SalesOrder salesOrder;

    @OneToOne(mappedBy = "finance", cascade = CascadeType.ALL)
    private Loan loan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public boolean isLoan() {
        return isLoan;
    }

    public void setLoan(boolean loan) {
        isLoan = loan;
    }

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Finance{");
        sb.append("id=").append(id);
        sb.append(", baseAmount=").append(baseAmount);
        sb.append(", taxAmount=").append(taxAmount);
        sb.append(", discountAmount=").append(discountAmount);
        sb.append(", isLoan=").append(isLoan);
        sb.append(", salesOrder=").append(salesOrder);
        sb.append(", loan=").append(loan);
        sb.append('}');
        return sb.toString();
    }
}
