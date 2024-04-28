package com.samdb.bloggingapp.controllers;

import com.samdb.bloggingapp.payloads.CommentDto;
import com.samdb.bloggingapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<CommentDto> commentDtos = this.commentService.getAllComments();
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @PostMapping("/user/{id1}/post/{id2}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable String id1, @PathVariable String id2) {
        Integer userId = Integer.parseInt(id1);
        Integer postId = Integer.parseInt(id2);

        CommentDto createdComment = this.commentService.createComment(commentDto, userId, postId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<CommentDto> readComment(@PathVariable String id) {
        Integer commentId = Integer.parseInt(id);
        CommentDto createdComment = this.commentService.readComment(commentId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable String id) {
        Integer commentId = Integer.parseInt(id);
        CommentDto updatedComment = this.commentService.updateComment(commentId, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable String id) {
        Integer commentId = Integer.parseInt(id);
        CommentDto deletedComment = this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(deletedComment, HttpStatus.CREATED);
    }
}
