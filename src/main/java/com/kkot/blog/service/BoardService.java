package com.kkot.blog.service;

import com.kkot.blog.dto.PageDTO;
import com.kkot.blog.dto.ReplySaveRequestDTO;
import com.kkot.blog.model.Board;
import com.kkot.blog.model.User;
import com.kkot.blog.repository.BoardRepository;
import com.kkot.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void save(Board requestBoard, User requestUser) { // title, content, count 그리고 User 데이터가 필요함
        requestBoard.setUser(requestUser);
        requestBoard.setHits(0);
        boardRepository.save(requestBoard);
    }

    public Page<Board> page(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public PageDTO getPageInfo(Page<Board> boardPage, int pageNo) {
        int totalPage = boardPage.getTotalPages();
        final int GROUP_SIZE = 2; // 페이지당 아이템 수
        // 현재 페이지를 통해 현재 페이지 그룹의 시작 페이지를 구함
        int startNumber = (int)((Math.floor(pageNo / GROUP_SIZE) * GROUP_SIZE) + 1 <= totalPage ? (Math.floor(pageNo / GROUP_SIZE) * GROUP_SIZE) + 1 : totalPage);

        // 전체 페이지 수와 현재 페이지 그룹의 시작 페이지를 통해 현재 페이지 그룹의 마지막 페이지를 구함
        int endNumber = (startNumber + GROUP_SIZE - 1 < totalPage ? startNumber + GROUP_SIZE - 1 : totalPage);

        // Prev, Next 버튼 활성화 변수
        boolean hasPrev = true;
        boolean hasNext = true;
        if(startNumber == 1) {
            hasPrev = false;
        }
        if(endNumber == totalPage) {
            hasNext = false;
        }

        /* 화면에는 원래 페이지 인덱스+1 로 출력됨을 주의 */
        int prevIndex = startNumber - GROUP_SIZE ;
        int nextIndex = startNumber + GROUP_SIZE;

        return new PageDTO(totalPage, pageNo, startNumber, endNumber, hasPrev, hasNext, prevIndex, nextIndex);
    }

    @Transactional
    public Board detail(int id){
        boardRepository.updateHits(id);
        return boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 상세보기 실패 id: " + id);
        });
    }

    @Transactional
    public void update(int id, Board requestBoard){
        // 1. 해당 Board 객체 영속화
        Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패 id: " + id);
        });
        // 2. 해당 객체 필드 값 변경
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 3. 해당 함수 종료 시(Service가 종료될 때) 트랜잭션 종료 -> 더티체킹 -> 자동 업데이트 flush
    }
    public void delete(int id){
        boardRepository.deleteById(id);
    }
}
