package com.samdb.bloggingapp.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Integer commentId;
    private String comment;
}
