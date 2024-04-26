package com.samdb.bloggingapp.payloads;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class MultiplePosts {
    private Integer userId;
    private Integer categoryId;
    private List<PostDto> posts;

    public MultiplePosts() {
        this.posts = new ArrayList<>();
    }
}
