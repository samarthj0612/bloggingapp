package com.samdb.bloggingapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer commentId;

    private String comment;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;
}
