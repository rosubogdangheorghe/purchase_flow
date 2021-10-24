package com.roki.purchase.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
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

    private BigDecimal purchaseFxRate;

    private BigDecimal receptionFxRate;


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


    //    orphanRemoval = true,cascade = CascadeType.ALL
    @OneToMany(mappedBy = "purchaseHeader")
    private List<PurchaseLineEntity> purchaseLineList;

    @ManyToOne
    @JoinColumn(name = "supplierId")
    private SupplierEntity supplier;

    @ManyToOne
    @JoinColumn(name = "statusId")
    private StatusEntity status;

    @ManyToOne
    @JoinColumn(name = "exchangeRateId")
    private ExchangeRateEntity currency;

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

    public BigDecimal getPurchaseFxRate() {
        return purchaseFxRate;
    }

    public void setPurchaseFxRate(BigDecimal purchaseFxRate) {
        this.purchaseFxRate = purchaseFxRate;
    }

    public BigDecimal getReceptionFxRate() {
        return receptionFxRate;
    }

    public void setReceptionFxRate(BigDecimal receptionFxRate) {
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

    public ExchangeRateEntity getCurrency() {
        return currency;
    }

    public void setCurrency(ExchangeRateEntity currency) {
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

    @Override
    public String toString() {
        return "PurchaseHeaderEntity{" +
                "purchaseHeaderId=" + purchaseHeaderId +
                ", purchaseNumber='" + purchaseNumber + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", receptionDate=" + receptionDate +
                ", purchaseFxRate=" + purchaseFxRate +
                ", receptionFxRate=" + receptionFxRate +
                ", supplierId=" + supplierId +
                ", currencyId=" + currencyId +
                ", statusId=" + statusId +
                ", departmentId=" + departmentId +
                ", userId=" + userId +
                ", purchaseLineList=" + purchaseLineList +
                ", supplier=" + supplier +
                ", status=" + status +
                ", currency=" + currency +
                ", department=" + department +
                ", user=" + user +
                '}';
    }
}
