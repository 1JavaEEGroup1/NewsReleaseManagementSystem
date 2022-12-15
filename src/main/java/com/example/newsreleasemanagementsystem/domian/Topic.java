package com.example.newsreleasemanagementsystem.domian;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
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
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "new_id")
    )
    @OrderBy("publishTime")
    @JsonIgnoreProperties({"topics,comments"})
    private Set<New> news;
}
