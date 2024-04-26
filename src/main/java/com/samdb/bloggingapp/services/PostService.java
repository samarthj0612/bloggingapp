package com.samdb.bloggingapp.services;

import com.samdb.bloggingapp.payloads.PostDto;
import com.samdb.bloggingapp.payloads.PostResponse;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();

    PostResponse getAllPostsWithParams(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    List<PostDto> getAllPostsByUser(Integer userId);

    List<PostDto> getAllPostsByCategory(Integer categoryId);

    List<PostDto> searchPosts(String keyword);

    List<PostDto> searchPostsByTitle(String title);

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto getPost(Integer postId);

    PostDto updatePost(PostDto postDto, Integer postId);

    PostDto deletePost(Integer postId);
}
