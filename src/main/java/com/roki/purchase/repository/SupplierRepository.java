package com.roki.purchase.repository;

import com.roki.purchase.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity,Integer>,
        PagingAndSortingRepository<SupplierEntity, Integer> {
}