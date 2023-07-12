package com.kkot.blog.repository;

import com.kkot.blog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

// DAO
// 자동으로 빈으로 등록이 됨
//@Repository // 생략 가능
public interface BoardRepository extends JpaRepository<Board, Integer> {
}
