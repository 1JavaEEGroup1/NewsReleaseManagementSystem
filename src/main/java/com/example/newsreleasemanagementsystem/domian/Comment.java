package com.example.newsreleasemanagementsystem.domian;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    private static final long serialVersionUID = 1L;

    //评论内容
    private String content;
    //发布时间
    private Date publishTime;
    //评论人
    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties({"email,password,phone,bio,news,roles,idols,fans"})
    private User author;
    //评论状态
    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
    //点赞数
    private static Integer likeNum;
    //二级评论
}
