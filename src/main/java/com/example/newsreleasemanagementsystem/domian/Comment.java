package com.example.newsreleasemanagementsystem.domian;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author jhlyh
 */
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class Comment {
    @Id
    private final Long commentId;

    @ManyToOne(targetEntity = User.class)
    private final User author;

    @ManyToOne(targetEntity = Comment.class)
    private final Comment comment;

    @ManyToOne(targetEntity = New.class)
    private final New news;

    private String content;
    private Integer state;
    private Date createTime;
    private Date freshTime;
}
