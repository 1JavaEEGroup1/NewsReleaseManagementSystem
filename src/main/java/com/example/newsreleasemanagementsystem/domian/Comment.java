package com.example.newsreleasemanagementsystem.domian;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    private final Long authorId;

    @ManyToOne(targetEntity = Comment.class)
    private final Long fatherId;

    @ManyToOne(targetEntity = New.class)
    private final Long newId;

    private String content;
    private Integer state;
    private Date createTime;
    private Date freshTime;
}
