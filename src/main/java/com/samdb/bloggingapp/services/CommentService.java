package com.samdb.bloggingapp.services;

import com.samdb.bloggingapp.payloads.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getAllComments();

    CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId);

    CommentDto readComment(Integer commentId);

    CommentDto updateComment(Integer commentId, CommentDto commentDto);

    CommentDto deleteComment(Integer commentId);

}
