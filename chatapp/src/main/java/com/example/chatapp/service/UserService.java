package com.example.chatapp.service;

import com.example.chatapp.entity.User;
import com.example.chatapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
/**
 * ✔ Spring이 이 클래스를 서비스(Service) 계층의 빈(Bean)으로 등록
 * ✔ 서비스 계층은 비즈니스 로직을 담당 (즉, 데이터를 조작하는 역할)
 * ✔ @Component의 확장 개념이지만, 서비스 역할을 명확히 하기 위해 @Service를 사용
 * */
public class UserService {
    /**
     * UserRepository를 이용해 DB에서 데이터를 저장하고 가져오는 역할을 수행
     * final을 붙여 의존성 주입(Dependency Injection, DI) 후 변경할 수 없도록 설정
     *
     * 📌 만약 final을 사용하지 않으면?
     * 클래스 내부에서 실수로 userRepository = null; 같은 변경이 가능해짐
     * final을 사용하면 userRepository가 한 번 할당된 후 변경되지 않음 (안전성 증가)
     * */
    private final UserRepository userRepository;

    /**
     * 생성자 주입
     * ✔ Spring이 UserRepository를 자동으로 주입(Inject)해 줌
     * ✔ 의존성 주입 방법 중 가장 추천되는 생성자 주입 방식
     * ✔ 생성자 주입을 사용하면 테스트하기도 쉬워짐
     *
     * 📌 만약 생성자 주입을 사용하지 않는다면?
     * @Autowired
     * private UserRepository userRepository;
     * -> @Autowired 필드 주입 방식은 가능하지만, 테스트하기 어려움 (비추천!)
     * -> 생성자 주입이 더 안정적이고 유지보수가 쉬움!
     * */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String password) {
        /**
         *  새로운 User 객체를 생성
         *  setUsername()과 setPassword()는 Lombok의 @Setter로 자동 생성됨
         *  userRepository.save(user)를 호출하면 DB에 INSERT INTO users (username, password) VALUES (?, ?); 실행
         *  저장된 User 객체를 반환
         * */
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user); // DB에 데이터 저장
    }
}
