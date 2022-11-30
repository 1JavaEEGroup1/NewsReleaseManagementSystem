package com.example.newsreleasemanagementsystem.controller;

import com.example.newsreleasemanagementsystem.domian.Comment;
import com.example.newsreleasemanagementsystem.domian.EState;
import com.example.newsreleasemanagementsystem.domian.State;
import com.example.newsreleasemanagementsystem.repository.CommentRepository;
import com.example.newsreleasemanagementsystem.repository.StateRepository;
import com.example.newsreleasemanagementsystem.util.ResponseResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private CommentRepository commentRepository;
    private StateRepository stateRepository;
    public CommentController(CommentRepository commentRepository, StateRepository stateRepository) {
        this.commentRepository = commentRepository;
        this.stateRepository = stateRepository;
    }
    /**
     * 添加评论
     */
    @PostMapping("/add")
    public ResponseResult<?> addComment(@RequestBody Comment comment) {
        return ResponseResult.success(commentRepository.save(comment));
    }

    /**
     * 评论列表
     */
    @GetMapping("/all")
    public ResponseResult<?> commentList() {
        return ResponseResult.success(commentRepository.findAll());
    }
    /**
     * 单个评论
     */
    @PostMapping("/detail")
    public ResponseResult<?> commentDetail(@RequestParam Long id) {
        if (!commentRepository.existsById(id)) {
            return ResponseResult.fail("comment id is not exist");
        } else {
            return ResponseResult.success(commentRepository.findById(id));
        }
    }

    /**
     * 下架评论（状态）
     */
    @PostMapping("/disableNew")
    public ResponseResult<?> disableComment(@RequestParam Long id) {
        if (!commentRepository.existsById(id)) {
            return ResponseResult.fail("comment id is not exist");
        } else {
            State state = stateRepository.findByName(EState.STATE_DisEnable).get();
            Comment comment = commentRepository.findById(id).get();
            comment.setState(state);
            return ResponseResult.success(commentRepository.save(comment));
        }
    }

    /**
     * 修改评论
     */
    @PostMapping("/change")
    public ResponseResult<?> change(@RequestBody Comment comment) {
        if (!commentRepository.existsById(comment.getId())) {
            return ResponseResult.fail("commment id is not exist");
        } else {
            return ResponseResult.success(commentRepository.save(comment));
        }
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/delete")
    public ResponseResult<?> delete(@RequestParam Long id) {
        if(! commentRepository.existsById(id)) {
            return ResponseResult.fail("comment id is not exist");
        } else {
            commentRepository.deleteById(id);
            return ResponseResult.success();
        }
    }
}
