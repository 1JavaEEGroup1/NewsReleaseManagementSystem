package com.example.newsreleasemanagementsystem.domian;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long commentId;

    private String CommentText;
    private Integer Status;
    private Date CreateTime;

    @OneToMany(mappedBy = "father")
    private Set<Comment> comments;

    @ManyToOne
    private Comment father;

    @ManyToOne
    @JoinTable(
            name = "author_comment",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private User commentAuthor;

    @ManyToOne
    @JoinTable(
            name = "new_comment",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "new_id")
    )
    private New aNew;
}
