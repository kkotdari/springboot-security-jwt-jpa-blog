package com.kkot.blog.config.auth;

import com.kkot.blog.model.User;
import com.kkot.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // 빈 등록
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    // 스프링이 로그인 요청을 가로챌 때 변수 username, password 2개를 가로채는데
    // 스프링이 password 부분 처리는 알아서 함
    // 우리는 username이 DB에 있는지만 확인하면 됨
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username).orElseThrow(()->{
            return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username);
        });
        return new PrincipalDetails(principal); // 시큐리티 세션에 UserDetails 타입의 정보가 담김
    }
}
