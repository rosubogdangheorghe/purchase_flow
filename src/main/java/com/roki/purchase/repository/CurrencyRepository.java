package com.roki.purchase.repository;

import com.roki.purchase.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity,Integer> {

    CurrencyEntity findByCurrency(String currency);
}
