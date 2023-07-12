package com.kkot.blog.service;

import com.kkot.blog.dto.ReplySaveRequestDTO;
import com.kkot.blog.model.Reply;
import com.kkot.blog.model.RoleType;
import com.kkot.blog.model.User;
import com.kkot.blog.repository.ReplyRepository;
import com.kkot.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReplyService {
    @Autowired
    private ReplyRepository replyRepository;

    public List<Reply> replyList(int boardId) {
        return replyRepository.findAllByBoardId(boardId);
    }

    @Transactional
    public void saveReply(ReplySaveRequestDTO replySaveRequestDTO) {
        int result = replyRepository.saveReply(replySaveRequestDTO.getUserId(), replySaveRequestDTO.getBoardId(), replySaveRequestDTO.getContent());
        System.out.println("BoardService : "+result);
    }

    @Transactional
    public void deleteReply(int replyId) {
        replyRepository.deleteById(replyId);
    }
}
