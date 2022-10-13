package com.manong.controller;

import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Comment;
import com.manong.domain.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult list(@RequestParam String commentType, @RequestParam Long articleId, @RequestParam Integer pageNum, @RequestParam Integer pageSize){
        return commentService.getCommentList(commentType,articleId,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){

        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkList(@RequestParam String commentType, @RequestParam Long articleId, @RequestParam Integer pageNum, @RequestParam Integer pageSize){
        return commentService.getCommentList(commentType,articleId,pageNum,pageSize);
    }
}
