package com.example.kiroku.repository;

import com.example.kiroku.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//UserRepository는 사용자 데이터를 DB에서 조회하거나 저장하기 위한 데이터 접근 계층
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
