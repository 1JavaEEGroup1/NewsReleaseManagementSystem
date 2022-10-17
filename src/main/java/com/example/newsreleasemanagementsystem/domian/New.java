package com.example.newsreleasemanagementsystem.domian;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author jhlyh
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class New {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long newId;

    private String title;
    private String artist;
    private String imgUrl;
    private Date CreateTime;
    private Integer viewTimes;

    @ManyToOne
    @JoinTable(
            name = "author_new",
            joinColumns = @JoinColumn(name = "new_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private User author;

    @OneToMany(mappedBy = "aNew")
    private Set<Comment> commentSet;

    @ManyToMany
    @JoinTable(
            name = "new_label",
            joinColumns = @JoinColumn(name = "new_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private Set<Label> labels;
}
