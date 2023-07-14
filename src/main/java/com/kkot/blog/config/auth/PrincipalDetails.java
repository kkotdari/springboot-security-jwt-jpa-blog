package com.kkot.blog.config.auth;

import com.kkot.blog.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션 저장소에 저장함
@Getter
public class PrincipalDetails implements UserDetails { // 스프링 시큐리티의 Security 세션에 들어가는 객체
    private User user; // 컴포지션: 클래스가 객체를 포함하는 것

    public PrincipalDetails(User user){
        this.user = user;
    }

    public int getId() { return user.getId(); }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getOAuth() { return user.getOauth(); }

    // 계정이 만료 여부 리턴
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠김 여부 리턴
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 여부 리턴
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부 리턴
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정의 권한 목록 리턴: 원칙적으로는 권한이 여러 개 있을 수 있어서 루프를 돌아야 함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();

//        collectors.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return "ROLE_" + user.getRole(); // ex: ROLE_USER
//            }
//        });
        // => 람다식
        collectors.add(()->{return "ROLE_" + user.getRole();});
        return collectors;
    }
}
