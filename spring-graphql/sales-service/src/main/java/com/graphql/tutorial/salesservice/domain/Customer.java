package com.graphql.tutorial.salesservice.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private LocalDate birthDate;

    private String fullName;

    private String phone;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<CustomerDocument> documents = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    @BatchSize(size = 50)
    private List<SalesOrder> salesOrders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<CustomerDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<CustomerDocument> documents) {
        this.documents = documents;
    }

    public List<SalesOrder> getSalesOrders() {
        return salesOrders;
    }

    public void setSalesOrders(List<SalesOrder> salesOrders) {
        this.salesOrders = salesOrders;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("id=").append(id);
        sb.append(", email='").append(email).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append(", fullName='").append(fullName).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", addresses=").append(addresses);
        sb.append('}');
        return sb.toString();
    }
}
