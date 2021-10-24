package com.roki.purchase.repository;

import com.roki.purchase.entity.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity,Integer> {


    ExchangeRateEntity findByCurrencyName(String currencyName);
}
