package com.roki.purchase.repository;

import com.roki.purchase.entity.PurchaseHeaderEntity;
import com.roki.purchase.entity.PurchaseLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseLineRepository extends JpaRepository<PurchaseLineEntity,Integer> {


    List<PurchaseLineEntity> findAllByPurchaseHeader(PurchaseHeaderEntity purchaseHeaderEntity);
    List<PurchaseLineEntity> findAllByPurchaseHeaderAndIsBudgetedIsNotNullAndBudgetLineNotNull(PurchaseHeaderEntity purchaseHeaderEntity);
}
