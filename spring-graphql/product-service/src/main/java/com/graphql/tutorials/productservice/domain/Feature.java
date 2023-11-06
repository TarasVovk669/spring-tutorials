package com.graphql.tutorials.productservice.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "features")
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean activeByDefault;
    private boolean activeByRequest;
    private BigDecimal installationPrice;
    private boolean isSafety;
    private boolean isEntertainment;
    private boolean isPerformance;
    private boolean isConvenience;
    private boolean isDisplay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActiveByDefault() {
        return activeByDefault;
    }

    public void setActiveByDefault(boolean activeByDefault) {
        this.activeByDefault = activeByDefault;
    }

    public boolean isActiveByRequest() {
        return activeByRequest;
    }

    public void setActiveByRequest(boolean activeByRequest) {
        this.activeByRequest = activeByRequest;
    }

    public BigDecimal getInstallationPrice() {
        return installationPrice;
    }

    public void setInstallationPrice(BigDecimal installationPrice) {
        this.installationPrice = installationPrice;
    }

    public boolean isSafety() {
        return isSafety;
    }

    public void setSafety(boolean safety) {
        isSafety = safety;
    }

    public boolean isEntertainment() {
        return isEntertainment;
    }

    public void setEntertainment(boolean entertainment) {
        isEntertainment = entertainment;
    }

    public boolean isPerformance() {
        return isPerformance;
    }

    public void setPerformance(boolean performance) {
        isPerformance = performance;
    }

    public boolean isConvenience() {
        return isConvenience;
    }

    public void setConvenience(boolean convenience) {
        isConvenience = convenience;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    public void setDisplay(boolean display) {
        isDisplay = display;
    }
}
