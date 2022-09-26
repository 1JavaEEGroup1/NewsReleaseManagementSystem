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
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE,force = true)
public class New {
    @Id
    private final Long newsId;

    @ManyToOne(targetEntity = Column.class)
    private final Long columnId;

    @ManyToOne(targetEntity = User.class)
    private final Long authorId;

    private String title;
    private String img;
    private String content;
    private Date releaseTime;
    private Date freshTime;
    private Integer pageView;
    private Integer commentNum;
}
