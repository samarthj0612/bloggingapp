package com.samdb.bloggingapp.repositories;

import com.samdb.bloggingapp.entities.Category;
import com.samdb.bloggingapp.entities.Post;
import com.samdb.bloggingapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByPostTitleContaining(String title);

    List<Post> findByPostContentContaining(String keyword);

//    @Query("SELECT p from Post p WHERE p.title like :key")
//    List<Post> searchByTitle(@Param("key") String title);
}
