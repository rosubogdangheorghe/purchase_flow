package com.roki.purchase.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "statuses")
public class StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusId;

    private String status;

    private Boolean enable;

    @OneToMany (mappedBy = "status")
    private List<PurchaseHeaderEntity> purchaseHeaderList;

    public Integer getStatusId() {
        return statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PurchaseHeaderEntity> getPurchaseHeaderList() {
        return purchaseHeaderList;
    }

    public void setPurchaseHeaderList(List<PurchaseHeaderEntity> purchaseHeaderList) {
        this.purchaseHeaderList = purchaseHeaderList;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
