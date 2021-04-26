package com.roki.purchase.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "budgetLines")
public class BudgetLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer budgetLineId;

    private String budgetLine;


    @OneToMany(mappedBy = "budgetLine")
    private List<PurchaseLineEntity> purchaseLineList;

    public Integer getBudgetLineId() {
        return budgetLineId;
    }

    public void setBudgetLineId(Integer budgetLineId) {
        this.budgetLineId = budgetLineId;
    }

    public String getBudgetLine() {
        return budgetLine;
    }

    public void setBudgetLine(String budgetLine) {
        this.budgetLine = budgetLine;
    }

    public List<PurchaseLineEntity> getPurchaseLineList() {
        return purchaseLineList;
    }

    public void setPurchaseLineList(List<PurchaseLineEntity> purchaseLineList) {
        this.purchaseLineList = purchaseLineList;
    }
}
