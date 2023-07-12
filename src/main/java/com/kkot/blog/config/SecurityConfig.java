package com.kkot.blog.config;

import com.kkot.blog.config.auth.PrincipalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 빈 등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration // 빈 등록(IoC 관리)
@EnableWebSecurity // 시큐리티 필터 등록 == 스프링 시큐리티의 설정을 이 파일에서 수행
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PrincipalDetailsService principalDetailsService;

    @Bean // IoC: 리턴값을 스프링이 관리함
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 시큐리티가 대신 로그인할 때 password를 가로채는데 해당 password가 뭘로 해시되어 회원가입이 되었는지 알아야
    // 같은 해시로 암호화해서 DB에 있는 해시값과 비교할 수 있음
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailsService).passwordEncoder(encodePwd());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // csrf 토큰 검증 비활성화 (개발 중 테스트 시 걸어두는 게 좋음)
            .authorizeRequests() // 요청 인증하기
//                .antMatchers("/**") // 이 경로로 들어오는 요청에 대해
                .antMatchers("/users/auth/**", "/api/v1/users/auth/**", "/js/**", "/image/**") // 이 경로로 들어오는 요청에 대해
                .permitAll() // 허용함
                .anyRequest() // 그 밖의 경로의 요청은
                .authenticated() // 인증되어야함
            .and() // 그리고
                .formLogin() // 폼로그인을 할거야
                .loginPage("/users/auth/login-form") // 폼로그인 경로는 여기야
                .loginProcessingUrl("/users/auth") // 스프링 시큐리티가 해당 경로로 요청오는 로그인을 가로채서 대신 로그인 해준다
                .defaultSuccessUrl("/boards"); // 로그인 성공시 이동할 경로
    }
}
