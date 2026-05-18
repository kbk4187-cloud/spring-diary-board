package com.example.kiroku.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
//UserEntity는 사용자 정보를 DB 테이블과 매핑하는 JPA 엔티티 클래스이며,
// 기본키(PK)인 id와 사용자 아이디, 비밀번호 등의 컬럼
@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // (나중에 암호화 추천)
}
