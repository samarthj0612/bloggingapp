package com.samdb.bloggingapp.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostDto {
    private Integer postId;
    private String postTitle;
    private String postContent;
    private String postImageName;
    private Date postCreatedAt;
    private UserDto user;
    private CategoryDto category;
}
