package com.kkot.blog.repository;

import com.kkot.blog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

// DAO
// 자동으로 빈으로 등록이 됨
//@Repository // 생략 가능
public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Modifying
    @Query(value = "UPDATE board SET hits = hits + 1 WHERE id=?1" ,nativeQuery = true)
    void updateHits(int id);
}
