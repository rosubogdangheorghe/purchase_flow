package com.roki.purchase.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "purchaseHeaders")
public class PurchaseHeaderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseHeaderId;

    private String purchaseNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate receptionDate;

    private Double purchaseFxRate;

    private Double receptionFxRate;

    @Column(insertable = false,updatable = false)
    private Integer supplierId;

    @Column(insertable = false,updatable = false)
    private Integer currencyId;

    @Column(insertable = false,updatable = false)
    private Integer statusId;

    @Column(insertable = false,updatable = false)
    private Integer departmentId;

    @Column(insertable = false,updatable = false)
    private Integer userId;

    @OneToMany(mappedBy = "purchaseHeader",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<PurchaseLineEntity> purchaseLineList;

    @ManyToOne
    @JoinColumn(name = "supplierId")
    private SupplierEntity supplier;

    @ManyToOne
    @JoinColumn(name = "statusId")
    private StatusEntity status;

    @ManyToOne
    @JoinColumn(name = "currencyId")
    private CurrencyEntity currency;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    private DepartmentEntity department;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;


    public Integer getPurchaseHeaderId() {
        return purchaseHeaderId;
    }

    public void setPurchaseHeaderId(Integer purchaseHeaderId) {
        this.purchaseHeaderId = purchaseHeaderId;
    }

    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(LocalDate receptionDate) {
        this.receptionDate = receptionDate;
    }

    public Double getPurchaseFxRate() {
        return purchaseFxRate;
    }

    public void setPurchaseFxRate(Double purchaseFxRate) {
        this.purchaseFxRate = purchaseFxRate;
    }

    public Double getReceptionFxRate() {
        return receptionFxRate;
    }

    public void setReceptionFxRate(Double receptionFxRate) {
        this.receptionFxRate = receptionFxRate;
    }

    public List<PurchaseLineEntity> getPurchaseLineList() {
        return purchaseLineList;
    }

    public void setPurchaseLineList(List<PurchaseLineEntity> purchaseLineList) {
        this.purchaseLineList = purchaseLineList;
    }

    public SupplierEntity getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


}
