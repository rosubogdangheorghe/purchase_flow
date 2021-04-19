package com.roki.purchase.repository;

import com.roki.purchase.entity.PurchaseLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseLineRepository extends JpaRepository<PurchaseLineEntity,Integer> {
}
