package com.roki.purchase.entity;
import javax.persistence.*;

@Entity
@Table(name = "purchaseLines")
public class PurchaseLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseBodyId;

    private String itemDescription;

    private String unitMeasure;

    private Double quantity;

    private Double unitPrice;

    private Boolean isBudget;

    private Double receivedQuantity;

    private Double receivedPrice;

    private Boolean isReceived;

    @Column(insertable = false,updatable = false)
    private Integer budgetLineId;

    @Column(insertable = false,updatable = false)
    private Integer purchaseHeaderId;

    @ManyToOne
    @JoinColumn(name = "purchaseHeaderId")
    private PurchaseHeaderEntity purchaseHeader;

    @ManyToOne
    @JoinColumn(name = "budgetLineId")
    private BudgetLineEntity budgetLine;

    public Integer getPurchaseBodyId() {
        return purchaseBodyId;
    }


    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Boolean getBudget() {
        return isBudget;
    }

    public void setBudget(Boolean budget) {
        isBudget = budget;
    }

    public Double getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(Double receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public Double getReceivedPrice() {
        return receivedPrice;
    }

    public void setReceivedPrice(Double receivedPrice) {
        this.receivedPrice = receivedPrice;
    }

    public Boolean getReceived() {
        return isReceived;
    }

    public void setReceived(Boolean received) {
        isReceived = received;
    }

    public PurchaseHeaderEntity getPurchaseHeader() {
        return purchaseHeader;
    }

    public void setPurchaseHeader(PurchaseHeaderEntity purchaseHeader) {
        this.purchaseHeader = purchaseHeader;
    }

    public BudgetLineEntity getBudgetLine() {
        return budgetLine;
    }

    public void setBudgetLine(BudgetLineEntity budgetLine) {
        this.budgetLine = budgetLine;
    }
}
