package com.roki.purchase.repository;

import com.roki.purchase.entity.PurchaseHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseHeaderRepository extends JpaRepository<PurchaseHeaderEntity,Integer> {
}
