package com.roki.purchase.repository;

import com.roki.purchase.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity,Integer> {

    AuthorityEntity findByAuthorityEquals(String authority);


}
