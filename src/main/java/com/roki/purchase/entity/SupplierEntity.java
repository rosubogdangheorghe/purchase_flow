package com.roki.purchase.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "suppliers")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierId;


    @Column(length = 8)
//    @Pattern(regexp = "[0-9]*",message = "Code must contain only figures")
//    @NotBlank(message = "Code can not be blanked")
    private Integer supplierCode;

    @Pattern(regexp ="[a-z,A-Z,0-9 -,&,!,.]*")
    private String supplierName;

    @Column(unique = true)
    private String vatCode;

    private String address;

    private String city;

    private String county;

    private String country;

    @Email
    private String email;

    private String contactPerson;

    private Boolean isBlocked;


    @OneToMany(mappedBy = "supplier")
    private List<PurchaseHeaderEntity> purchaseHeaderList;
    

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(Integer supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public List<PurchaseHeaderEntity> getPurchaseHeaderList() {
        return purchaseHeaderList;
    }

    public void setPurchaseHeaderList(List<PurchaseHeaderEntity> purchaseHeaderList) {
        this.purchaseHeaderList = purchaseHeaderList;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}
