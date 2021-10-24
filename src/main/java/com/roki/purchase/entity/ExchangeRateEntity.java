package com.roki.purchase.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "exchangeRates")
public class ExchangeRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exchangeRateId;


    private BigDecimal value;

    private String currencyName;

    private BigDecimal multiplier;


    @OneToMany(mappedBy = "currency")
    private List<PurchaseHeaderEntity> purchaseHeaderList;


    public Integer getExchangeRateId() {
        return exchangeRateId;
    }

    public void setExchangeRateId(Integer exchangeRateId) {
        this.exchangeRateId = exchangeRateId;
    }
    
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public List<PurchaseHeaderEntity> getPurchaseHeaderList() {
        return purchaseHeaderList;
    }

    public void setPurchaseHeaderList(List<PurchaseHeaderEntity> purchaseHeaderList) {
        this.purchaseHeaderList = purchaseHeaderList;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public BigDecimal getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(BigDecimal multiplier) {
        this.multiplier = multiplier;
    }
}
