package com.kkot.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content; // 섬머노트 라이브러리 사용: <html> 태그가 포함되어 디자인 됨

    private int hits;

    // Many = Board : One = User
    // fetch = EAGER 전략: 무조건 가져온다.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId") // 실제 테이블에서 생기는 컬럼명
    private User user; // DB는 오브젝트를 저장할 수 없어서 FK를 사용하지만 자바는 오브젝트를 저장할 수 있다.

    // mappedBy: 연관관계의 주인이 아니다. (FK가 아님) DB에 컬럼을 만들지 않음. Reply 엔티티의 board 필드에 매핑함
    // fetch = EAGER 전략: 무조건 같이 가져온다. (처음에 댓글창이 표시되는 경우)
    // fetch = 기본 LAZY 전략: 필요할 때 가져온다. (처음에 댓글창이 접혀있는 경우)
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc")
    // @JoinColumn(name = "replyId") 불필요. 왜냐하면 Board 테이블에 reply_id는 필요하지 않기 때문
    private List<Reply> replies;

    @CreationTimestamp
    private Timestamp createdTime;
}
