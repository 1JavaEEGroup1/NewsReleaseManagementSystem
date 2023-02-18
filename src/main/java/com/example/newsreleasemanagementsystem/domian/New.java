package com.example.newsreleasemanagementsystem.domian;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
    @JsonIgnoreProperties({"email","password","phone","bio","headshotsUrl","state","news","roles","idols","fans"})
    private User author;
    //新闻状态
    @ManyToOne
    @JoinColumn(name = "state_id")
    @JsonIgnoreProperties({"name"})
    private State state;
    //标签
    @ManyToMany
    @JsonIgnoreProperties({"name","coverUrl","news"})
    private List<Topic> topics;
    //已阅读人数
    private Integer readNum;
    //点赞人数
    private Integer likeNum;
    //评论
    @OneToMany
    @JoinColumn(name = "comment_id")
    @JsonIgnoreProperties({"content","publishTime","author","state","likeNum"})
    private List<Comment> comments;
}
