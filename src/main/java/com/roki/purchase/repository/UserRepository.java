package com.roki.purchase.repository;

import com.roki.purchase.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    UserEntity findByUsername(String username);

    @Query("FROM UserEntity user JOIN AuthorityEntity auth ON auth.user.userId = user.userId WHERE auth.authority = :authority")
    List<UserEntity> findAllUserByAuthority(@Param("authority")String authority);

}
