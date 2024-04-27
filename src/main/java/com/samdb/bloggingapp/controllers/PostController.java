package com.samdb.bloggingapp.controllers;

import com.samdb.bloggingapp.configs.Constants;
import com.samdb.bloggingapp.payloads.FileResponse;
import com.samdb.bloggingapp.payloads.MultiplePosts;
import com.samdb.bloggingapp.payloads.PostDto;
import com.samdb.bloggingapp.payloads.PostResponse;
import com.samdb.bloggingapp.services.FileService;
import com.samdb.bloggingapp.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postDtos = this.postService.getAllPosts();
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PostResponse> getAllPostsWithParams(
            @RequestParam(value = "pageNumber", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = Constants.DEFAULT_PAGE_SORT_ORDER, required = false) String sortOrder
    ) {
        PostResponse postResponse = this.postService.getAllPostsWithParams(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/user/{Id}")
    public ResponseEntity<List<PostDto>> getAllPostsByUser(@PathVariable String Id) {
        Integer userId = Integer.parseInt(Id);
        List<PostDto> postDtos = this.postService.getAllPostsByUser(userId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/category/{Id}")
    public ResponseEntity<List<PostDto>> getAllPostsByCategory(@PathVariable String Id) {
        Integer categoryId = Integer.parseInt(Id);
        List<PostDto> postDtos = this.postService.getAllPostsByCategory(categoryId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keyword) {
        List<PostDto> postDtos = this.postService.searchPosts(keyword);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/search/title/{title}")
    public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable String title) {
        List<PostDto> postDtos = this.postService.searchPostsByTitle(title);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable(name = "userId") String id1, @PathVariable(name = "categoryId") String id2) {
        Integer userId = Integer.parseInt(id1);
        Integer categoryId = Integer.parseInt(id2);

        PostDto createdPostDto = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    @PostMapping("/multiple/user/{userId}/category/{categoryId}")
    public ResponseEntity<MultiplePosts> createMultiplePost(@RequestBody MultiplePosts multiplePosts, @PathVariable(name = "userId") String id1, @PathVariable(name = "categoryId") String id2) {
        Integer userId = Integer.parseInt(id1);
        Integer categoryId = Integer.parseInt(id2);

        MultiplePosts createdPostDtos = new MultiplePosts();
        for (PostDto postDto : multiplePosts.getPosts()) {
            PostDto createdPostDto = this.postService.createPost(postDto, userId, categoryId);
            createdPostDtos.getPosts().add(createdPostDto);
        }
        createdPostDtos.setUserId(userId);
        createdPostDtos.setCategoryId(categoryId);

        return new ResponseEntity<>(createdPostDtos, HttpStatus.CREATED);
    }

    @GetMapping("/post/{Id}")
    public ResponseEntity<PostDto> getPost(@PathVariable String Id) {
        Integer postId = Integer.parseInt(Id);
        PostDto postDto = this.postService.getPost(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @PutMapping("/post/{Id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable String Id) {
        Integer postId = Integer.parseInt(Id);
        PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

    @DeleteMapping("/post/{Id}")
    public ResponseEntity<PostDto> deletePost(@PathVariable String Id) {
        Integer postId = Integer.parseInt(Id);
        PostDto deletedPostDto = this.postService.deletePost(postId);
        return new ResponseEntity<>(deletedPostDto, HttpStatus.OK);
    }

    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<FileResponse> uploadImage(@RequestParam("image") MultipartFile multipartFile, @PathVariable("postId") String id) {
        Integer postId = Integer.parseInt(id);
        PostDto postDto = this.postService.getPost(postId);

        String filename = null;
        try {
            filename = this.fileService.uploadResources(path, multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            FileResponse fileResponse = new FileResponse(null, "Failed to upload File");
            return new ResponseEntity<>(fileResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        postDto.setPostImageName(filename);
        PostDto updatedPostDto = this.postService.updatePost(postDto, postId);

        FileResponse fileResponse = new FileResponse(filename, "File Successfully uploaded");
        return new ResponseEntity<>(fileResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/image/fetch/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void fetchImage(@PathVariable String imageName, HttpServletResponse response) {
        try {
            InputStream resource = this.fileService.fetchResources(path, imageName);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}
