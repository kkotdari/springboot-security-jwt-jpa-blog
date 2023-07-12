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
        System.out.println("BoardApiController.update");
        System.out.println("id = " + id + ", requestBoard.tltle = " + requestBoard.getTitle() +", requestBoard.content = " + requestBoard.getContent());
        boardService.update(id, requestBoard);
        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }
    @DeleteMapping("/{id}")
    public ResponseDTO<Integer> delete(@PathVariable int id) {
        boardService.delete(id);
        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }

    // 댓글 부분
    // 데이터 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
    // dto 사용하지 않은 이유는!!
    @PostMapping("/{boardId}/replies")
    public ResponseDTO<Integer> replySave(@RequestBody ReplySaveRequestDTO replySaveRequestDto) {
        System.out.println("BoardApiController.replySave");
        System.out.println("replySaveRequestDto.userId = " + replySaveRequestDto.getUserId());
        replyService.saveReply(replySaveRequestDto);
        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/{boardId}/replies/{replyId}")
    public ResponseDTO<Integer> replyDelete(@PathVariable int replyId) {
        replyService.deleteReply(replyId);
        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }
}
