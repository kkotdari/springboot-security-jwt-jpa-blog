package com.kkot.blog.repository;

import com.kkot.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// DAO
// 자동으로 빈으로 등록이 됨
//@Repository // 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {
    // SELECT * FROM user WHERE username = ?1;
    Optional<User> findByUsername(String username);
}

// JPA Naming 쿼리
// SELECT * FROM user WHERE username = ?1 AND password = ?2 // ?에는 파라미터가 순서대로 매핑됨
//    User findByUsernameAndPassword(String username, String password);

// 위와 같은 기능을 하는 JPA Native Query
//    @Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//    User login(String username, String password);
