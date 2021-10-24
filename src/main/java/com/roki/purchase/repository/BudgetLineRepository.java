package com.roki.purchase.repository;

import com.roki.purchase.entity.BudgetLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetLineRepository extends JpaRepository<BudgetLineEntity,Integer> {

    BudgetLineEntity findByBudgetLine(String budgetLine);
}
