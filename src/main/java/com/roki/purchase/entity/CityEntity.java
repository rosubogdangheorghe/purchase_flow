package com.roki.purchase.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cities")
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cityId;


    private String city;

//    @OneToMany(mappedBy = "city")
//    private List<SupplierEntity> supplierList;


    public Integer getCityId() {
        return cityId;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

//    public List<SupplierEntity> getSupplierList() {
//        return supplierList;
//    }
//
//    public void setSupplierList(List<SupplierEntity> supplierList) {
//        this.supplierList = supplierList;
//    }
}
