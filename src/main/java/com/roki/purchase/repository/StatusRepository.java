package com.roki.purchase.repository;

import com.roki.purchase.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity,Integer> {

    List<StatusEntity> findAllByStatus(String status);

    StatusEntity findByStatus(String status);
}
