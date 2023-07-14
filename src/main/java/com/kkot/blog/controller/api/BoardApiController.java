package com.kkot.blog.controller.api;

import com.kkot.blog.config.auth.PrincipalDetails;
import com.kkot.blog.dto.ReplySaveRequestDTO;
import com.kkot.blog.dto.ResponseDTO;
import com.kkot.blog.model.Board;
import com.kkot.blog.model.Reply;
import com.kkot.blog.service.BoardService;
import com.kkot.blog.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
public class BoardApiController {
    private final int PAGE_BOARD_COUNT = 5;
    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyService replyService;

    @GetMapping("/{id}/replies")
    public List<Reply> getReplyList(@PathVariable int id){
        return boardService.detail(id).getReplies();
    }

    @PostMapping
    public ResponseDTO<Integer> postBoard(@RequestBody Board requestBoard, @AuthenticationPrincipal PrincipalDetails principal) {
        boardService.save(requestBoard, principal.getUser());
        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴 (Jackson 라이브러리)
    }

    @PutMapping("/{id}")
    public ResponseDTO<Integer> update(@PathVariable int id, @RequestBody Board requestBoard) {
        boardService.update(id, requestBoard);
        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }
    @DeleteMapping("/{id}")
    public ResponseDTO<Integer> delete(@PathVariable int id) {
        boardService.delete(id);
        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }
}
