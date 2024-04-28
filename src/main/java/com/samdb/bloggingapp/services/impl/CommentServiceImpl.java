package com.samdb.bloggingapp.services.impl;

import com.samdb.bloggingapp.entities.Comment;
import com.samdb.bloggingapp.entities.Post;
import com.samdb.bloggingapp.entities.User;
import com.samdb.bloggingapp.exceptions.ResourceNotFound;
import com.samdb.bloggingapp.payloads.CommentDto;
import com.samdb.bloggingapp.repositories.CommentRepo;
import com.samdb.bloggingapp.repositories.PostRepo;
import com.samdb.bloggingapp.repositories.UserRepo;
import com.samdb.bloggingapp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = this.commentRepo.findAll();
        List<CommentDto> commentDtos;
        commentDtos = comments.stream().map(comment -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "userId", userId));
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFound("Post", "postId", postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setUser(user);
        comment.setPost(post);

        Comment savedComment = this.commentRepo.save(comment);
        CommentDto savedCommentDto;
        savedCommentDto = this.modelMapper.map(savedComment, CommentDto.class);
        return savedCommentDto;
    }

    @Override
    public CommentDto readComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFound("Comment", "commentId", commentId));
        CommentDto commentDto;
        commentDto = this.modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    @Override
    public CommentDto updateComment(Integer commentId, CommentDto commentDto) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFound("Comment", "commentId", commentId));
        if (!commentDto.getComment().isEmpty()) {
            comment.setComment(commentDto.getComment());
            this.commentRepo.save(comment);
        }

        CommentDto updatedCommentDto;
        updatedCommentDto = this.modelMapper.map(comment, CommentDto.class);
        return updatedCommentDto;
    }

    @Override
    public CommentDto deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFound("Comment", "commentId", commentId));
        this.commentRepo.delete(comment);
        return this.modelMapper.map(comment, CommentDto.class);
    }
}
