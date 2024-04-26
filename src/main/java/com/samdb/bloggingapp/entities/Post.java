package com.samdb.bloggingapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer postId;

    @Column(name = "title", length = 100, nullable = false)
    private String postTitle;

    @Column(name = "content", length = 10000)
    private String postContent;

    @Column(name = "imageName")
    private String postImageName;

    @Column(name = "createdAt")
    private Date postCreatedAt;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    // @JoinColumn(name = "category_id")
    private Category category;
}
