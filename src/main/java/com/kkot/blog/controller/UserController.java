package com.kkot.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkot.blog.config.auth.PrincipalDetails;
import com.kkot.blog.model.KakaoProfile;
import com.kkot.blog.model.OAuthToken;
import com.kkot.blog.model.User;
import com.kkot.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 등 허용
@Controller
@RequestMapping("/users")
public class UserController {
    @Value("${kkot.key}")
    private String kkotKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping("/auth/register-form")
    public String registerForm(){
        return "user/register-form";
    }

    @GetMapping("/auth/login-form")
    public String loginForm(){
        System.out.println("UserController.loginForm");
        return "user/login-form";
    }

    @GetMapping("/update-form")
    public String updateForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        model.addAttribute("principal", userService.find(principalDetails.getUser().getId()));
        return "user/update-form";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수

        // POST방식으로 key=value 데이터를 요청 (카카오쪽으로)
        // Retrofit2
        // OkHttp
        // RestTemplate

        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "f39050ec53184eddbfff9a9658afaf80");
        params.add("redirect_uri", "http://localhost:8080/users/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("카카오 엑세스 토큰 : "+oauthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );
        System.out.println(response2.getBody());

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // User 오브젝트 : username, password, email
        System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
        System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());

        System.out.println("블로그서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
        System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        // UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
        System.out.println("블로그서버 패스워드 : " + kkotKey);

        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
                .password(kkotKey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();

        // 가입자 혹은 비가입자 체크 해서 처리
        User originUser = userService.find(kakaoUser.getUsername());

        if(originUser.getUsername() == null) {
            System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다");
            userService.register(kakaoUser);
        }

        System.out.println("자동 로그인을 진행합니다.");
        // 로그인 처리
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), kkotKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }
}
