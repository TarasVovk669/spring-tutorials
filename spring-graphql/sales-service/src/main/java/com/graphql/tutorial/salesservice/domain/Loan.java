package com.graphql.tutorial.salesservice.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String financeCompany;

    private String contactPersonName;

    private String contactPersonPhone;

    private String contactPersonEmail;

    @OneToOne
    @JoinColumn(name = "finance_id")
    private Finance finance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFinanceCompany() {
        return financeCompany;
    }

    public void setFinanceCompany(String financeCompany) {
        this.financeCompany = financeCompany;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public Finance getFinance() {
        return finance;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Loan{");
        sb.append("id=").append(id);
        sb.append(", financeCompany='").append(financeCompany).append('\'');
        sb.append(", contactPersonName='").append(contactPersonName).append('\'');
        sb.append(", contactPersonPhone='").append(contactPersonPhone).append('\'');
        sb.append(", contactPersonEmail='").append(contactPersonEmail).append('\'');
        sb.append(", finance=").append(finance);
        sb.append('}');
        return sb.toString();
    }
}
