package com.example.chatapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // 이 클래스가 JPA 엔티티(Entity) 임을 나타냄 (DB 테이블로 매핑됨) -> 이 어노테이션이 없으면 JPA가 인식하지 않음
@Getter // Lombok을 사용하여 getter, setter 자동 생성
@Setter
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 포함한 생성자 자동 추가
@Table(name="users") // 이 엔티티가 users 테이블과 매핑됨을 지정 (기본적으로 클래스 이름을 소문자로 변환한것이 테이블명)
// 여기서는 테이블 이름을 users로 지정
public class User {

    @Id // 기본 키(Primary Key) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키를 자동 증가 (Auto Increment) 설정
    private Long id;

    @Column(nullable = false, unique = true) // DB 컬럼에 NOT NULL 및 UNIQUE 제약 조건 추가
    private String username;

    @Column(nullable = false) // DB 컬럼에 NOT NULL 제약 조건 추가
    private String password;
}
