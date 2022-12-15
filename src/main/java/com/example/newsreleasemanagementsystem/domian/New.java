package com.example.newsreleasemanagementsystem.domian;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class New implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    private static final long serialVersionUID = 1L;
    //封面
    private String coverUrl;
    //标题
    private String title;
    //内容
    private String content;
    //发布时间
    private Date publishTime;
    //作者
    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties({"email,password,phone,bio,news,roles,idols,fans"})
    private User author;
    //新闻状态
    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
    //标签
    @ManyToMany(mappedBy = "news")
    @JsonIgnoreProperties({"news"})
    private Set<Topic> topics;
    //已阅读人数
    private static Integer readNum;
    //点赞人数
    private static Integer likeNum;
    //评论
    @OneToMany
    @JoinColumn(name = "comment_id")
    private Set<Comment> comments;
}
