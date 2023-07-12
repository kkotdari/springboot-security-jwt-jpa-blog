package com.kkot.blog.repository;

import com.kkot.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// DAO
// 자동으로 빈으로 등록이 됨
//@Repository // 생략 가능
public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    @Modifying
    @Query(value="INSERT INTO reply(user_id, board_id, content, created_time) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
    int saveReply(int userId, int boardId, String content); // 업데이트된 행의 개수를 리턴해줌.

    @Query(value="SELECT * FROM reply WHERE board_id = ?1 ORDER BY id", nativeQuery = true)
    List<Reply> findAllByBoardId(int boardId);
}
