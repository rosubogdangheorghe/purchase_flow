package com.roki.purchase.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "currencies")
public class CurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer currencyId;


    private String currency;


    @OneToMany(mappedBy = "currency")
    private List<PurchaseHeaderEntity> purchaseHeaderList;

    public Integer getCurrencyId() {
        return currencyId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<PurchaseHeaderEntity> getPurchaseHeaderList() {
        return purchaseHeaderList;
    }

    public void setPurchaseHeaderList(List<PurchaseHeaderEntity> purchaseHeaderList) {
        this.purchaseHeaderList = purchaseHeaderList;
    }
}
