package com.kkot.blog.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // User 클래스는 스프링 부트 프로젝트가 실행될 때 자동으로 MySQL에 테이블로 생성됨
//@DynamicInsert // null이 아닌 필드만 INSERT
public class User {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. Oracle: SEQUENCE, MySQL: AUTO_INCREMENT
    private int id; // 시퀀스, AUTO_INCREMENT

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 100) // 비밀번호: 암호화했을 때의 길이를 고려하여 설정
    private String password;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

//    @ColumnDefault("'user'") // 디폴트 값 설정
    // DB에는 RoleType이 없으므로 Enum의 타입을 알려줘야 한다.
    // @Enumerated: 필드의 타입으로 Enum 타입을 사용
    @Enumerated(EnumType.STRING)
    private RoleType role; // Enum을 쓰는 게 좋다.

    private String oauth; // kakao, google

    @CreationTimestamp // 생성시 시간이 자동 입력
    private Timestamp createdTime;
}
