package com.roki.purchase.repository;

import com.roki.purchase.entity.PurchaseHeaderEntity;
import com.roki.purchase.entity.PurchaseLineEntity;
import com.roki.purchase.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseHeaderRepository extends JpaRepository<PurchaseHeaderEntity,Integer> {

   List<PurchaseHeaderEntity> findAllByUserId(Integer userId);
    List<PurchaseHeaderEntity> findAllByUserIdAndStatusEquals(Integer userId, StatusEntity status);

    PurchaseHeaderEntity findByPurchaseLineList(PurchaseLineEntity purchaseLineEntity);
    PurchaseHeaderEntity findByPurchaseHeaderId(int id);

    List<PurchaseHeaderEntity> findAllByStatusEquals(StatusEntity status);

    List<PurchaseHeaderEntity> findAllByStatusEqualsOrStatusEquals(StatusEntity statusOne,StatusEntity statusTwo);

}
