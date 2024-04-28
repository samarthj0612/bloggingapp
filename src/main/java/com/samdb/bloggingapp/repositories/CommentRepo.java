package com.samdb.bloggingapp.repositories;

import com.samdb.bloggingapp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
