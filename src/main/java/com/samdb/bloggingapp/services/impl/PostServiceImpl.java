package com.samdb.bloggingapp.services.impl;

import com.samdb.bloggingapp.entities.Category;
import com.samdb.bloggingapp.entities.Post;
import com.samdb.bloggingapp.entities.User;
import com.samdb.bloggingapp.exceptions.ResourceNotFound;
import com.samdb.bloggingapp.payloads.PostDto;
import com.samdb.bloggingapp.payloads.PostResponse;
import com.samdb.bloggingapp.repositories.CategoryRepo;
import com.samdb.bloggingapp.repositories.PostRepo;
import com.samdb.bloggingapp.repositories.UserRepo;
import com.samdb.bloggingapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = this.postRepo.findAll();

        List<PostDto> postDtos;
        postDtos = posts.stream().map((post) -> this.postToPostDto(post)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public PostResponse getAllPostsWithParams(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Page Number: {} | Page Size: {}", pageNumber, pageSize);

        Sort sort = Sort.by(sortBy);
        if (sortOrder.equalsIgnoreCase("asc")) sort = Sort.by(sortBy).ascending();
        else if (sortOrder.equalsIgnoreCase("desc")) sort = Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> page = this.postRepo.findAll(pageable);
        List<Post> posts = page.getContent();
        List<PostDto> postDtos = posts.stream()
                .map(this::postToPostDto)
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPosts(postDtos);
        postResponse.setPageNumber(page.getNumber());
        postResponse.setPageSize(page.getSize());
        postResponse.setTotalElements(page.getTotalElements());
        postResponse.setTotalPages(page.getTotalPages());
        postResponse.setLastPage(page.isLast());

        return postResponse;
    }

    @Override
    public List<PostDto> getAllPostsByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "Id", userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos;
        postDtos = posts.stream().map((post) -> this.postToPostDto(post)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getAllPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFound("Category", "Id", categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);
        List<PostDto> postDtos;
        postDtos = posts.stream().map((post) -> this.postToPostDto(post)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.findByPostContentContaining(keyword);
        List<PostDto> postDtos;
        postDtos = posts.stream().map((post) -> this.postToPostDto(post)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> searchPostsByTitle(String title) {
        List<Post> posts = this.postRepo.findByPostTitleContaining(title);
        List<PostDto> postDtos;
        postDtos = posts.stream().map((post) -> this.postToPostDto(post)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("UserId", "Id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFound("CategoryId", "Id", categoryId));

        Post post = this.postDtoToPost(postDto);
        post.setPostCreatedAt(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepo.save(post);
        PostDto newPostDto;
        newPostDto = this.modelMapper.map(newPost, PostDto.class);
        return newPostDto;
    }

    @Override
    public PostDto getPost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFound("Post", "Id", postId));
        PostDto postDto;
        postDto = this.modelMapper.map(post, PostDto.class);

        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFound("Post", "Id", postId));

        post.setPostTitle(postDto.getPostTitle());
        post.setPostContent(postDto.getPostContent());
        post.setPostImageName(postDto.getPostImageName());

        Post updatedPost = this.postRepo.save(post);
        PostDto updatedPostDto;
        updatedPostDto = this.postToPostDto(updatedPost);
        return updatedPostDto;
    }

    @Override
    public PostDto deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFound("Post", "Id", postId));
        this.postRepo.delete(post);

        PostDto deletedPostDto;
        deletedPostDto = this.postToPostDto(post);
        return deletedPostDto;
    }

    private Post postDtoToPost(PostDto postDto) {
        Post post;
        post = this.modelMapper.map(postDto, Post.class);
        return post;
    }

    private PostDto postToPostDto(Post post) {
        PostDto postDto;
        postDto = this.modelMapper.map(post, PostDto.class);
        return postDto;
    }
}
