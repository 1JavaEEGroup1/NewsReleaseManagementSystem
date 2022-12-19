package com.example.newsreleasemanagementsystem.domian;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class Topic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    private static final long serialVersionUID = 1L;

    //标签名字
    private String name;
    //标签封面
    private String coverUrl;
    //所含新闻
    @ManyToMany(mappedBy = "topics")
    @JsonIgnoreProperties({"coverUrl","title","content","publishTime","author","state","topics","readNum","likeNum","comments"})
    private List<New> news;
}
