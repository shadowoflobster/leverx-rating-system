package com.example.ratingsystem.adapters.outbound.persistence.repositories;

import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u " +
            "WHERE LOWER(CONCAT(u.firstName || ' ' ||  u.lastName)) LIKE LOWER(:fullName)")
    List<UserEntity> findByFullName(@Param("fullName") String fullName);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByApprovedFalse();

    void deleteByEmail(String email);


}
