package com.roki.purchase.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "countries")
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer countryId;


    private String countryCode;


    private String countryName;
//
//    @OneToMany(mappedBy = "country")
//    private List<SupplierEntity> supplierList;

    public Integer getCountryId() {
        return countryId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

//    public List<SupplierEntity> getSupplierList() {
//        return supplierList;
//    }
//
//    public void setSupplierList(List<SupplierEntity> supplierList) {
//        this.supplierList = supplierList;
//    }
}
