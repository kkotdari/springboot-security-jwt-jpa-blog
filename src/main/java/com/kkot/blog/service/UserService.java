package com.kkot.blog.service;

import com.kkot.blog.model.RoleType;
import com.kkot.blog.model.User;
import com.kkot.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void register(User user){
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword); // password를 해시 암호화
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User find(String username) {
        User user = userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });
        return user;
    }

    @Transactional
    public void update(User requestUser){
        User user = userRepository.findById(requestUser.getId()).orElseThrow(()->{
            return new IllegalArgumentException("수정하려는 회원이 없습니다 id: " + requestUser.getId());
        });
        // Validation 체크: OAuth 로그인 회원은 비밀번호, 이메일을 수정할 수 없다.
        if(user.getOauth() == null || user.getOauth() == "") {
            String rawPassword = requestUser.getPassword();
            String encPassword = encoder.encode(rawPassword);
            user.setPassword(encPassword);
            user.setEmail(requestUser.getEmail());
        }
        // UserService.update 메서드 종료 -> 서비스 종료 -> 트랜잭션 종료 -> commit 수행
        // 영속화된 user 객체의 변경이 감지되면 더티체킹이 수행되어 update 쿼리를 수행
    }
}
