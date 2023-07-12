package com.kkot.blog.controller;

import com.kkot.blog.config.auth.PrincipalDetails;
import com.kkot.blog.dto.PageDTO;
import com.kkot.blog.model.Board;
import com.kkot.blog.service.BoardService;
import com.kkot.blog.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/boards")
public class BoardController {
    private final int PAGE_BOARD_COUNT = 5;
    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyService replyService;

    @GetMapping
    public String board(@RequestParam(value="page", required=false) String page, Model model) {
        model.addAttribute("page", page);
        if(page == null){
            model.addAttribute("page", "0");
        }
        return "board/list";
    }

    //////////// 두 메서드 통합하기 ~
    @GetMapping("/list")
    public String boardList(@PageableDefault(size = PAGE_BOARD_COUNT, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<Board> boardPage = boardService.page(pageable);
        model.addAttribute("boardPage", boardPage.getContent());
        return "fragment/board-list :: boardList";
    }

    @GetMapping("/pagination")
    public String boardPagination(@PageableDefault(size = PAGE_BOARD_COUNT, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<Board> boardPage = boardService.page(pageable);
        PageDTO pageDTO = boardService.getPageInfo(boardPage, pageable.getPageNumber());
        model.addAttribute("pageInfo", pageDTO);
        return "fragment/board-pagination :: boardPagination";
    }
    //////////// ~ 두 메서드 통합하기

    @GetMapping("/{id}/replies")
    public String boardList(@PathVariable("id") int boardId, Model model) {
        System.out.println("boardId = " + boardId);
        model.addAttribute("replyList", replyService.replyList(boardId));
        return "fragment/reply-list :: replyList";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        model.addAttribute("board", boardService.detail(id));
        model.addAttribute("principal", principalDetails.getUser());
        return "board/detail";
    }

    @GetMapping("/save-form")
    public String saveForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        model.addAttribute("principal", principalDetails.getUser());
        return "/board/save-form";
    }

    @GetMapping("/update-form/{id}")
    public String modifyForm(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.detail(id));
        return "/board/update-form";
    }
}
