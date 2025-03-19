package com.example.chatapp.repository;

import com.example.chatapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 이 인터페이스가 JPA Repository 임을 나타냄
// Spring이 이 인터페이스를 Repository(DAO)로 인식하도록 함
// @Repository 없이도 JpaRepository를 상속하면 자동으로 빈 등록됨
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * JpaRepository는 기본적으로 SQL 쿼리를 실행하는 기능을 제공함.
     * JPA가 데이터베이스와 객체(Entity) 간의 데이터 매핑을 자동으로 처리해 줌.
     * findById(), findAll(), save(), deleteById() 등의 메서드를 통해 SQL을 자동 실행할 수 있음.
     * JPA가 User 엔티티를 관리하도록 설정
     * Long → id의 타입이 Long이므로 지정
     * */

    User findByUsername(String username);

    boolean existsByUsername(String username);
    /**
     * Spring Data JPA가 자동으로 SQL을 생성
     * 아래 SQL과 동일한 쿼리 실행:
     * SELECT * FROM users WHERE username = ?;
     * SQL을 직접 작성할 필요 없이 메서드명만 지정하면 됨
     * */

}
