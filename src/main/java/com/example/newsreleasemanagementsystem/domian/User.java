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
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE,force = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long userId;

    private String name;
    private String password;
    private Integer userType;
    private String phone;
    private String email;
    private String imgUrl;
    private Date CreateTime;
    private Date FlashTime;


    @ManyToOne
    private User idol;

    @OneToMany(mappedBy = "idol")
    private Set<User> fans;

    @OneToMany(mappedBy = "author")
    private Set<New> newSet;

    @OneToMany(mappedBy = "commentAuthor")
    private Set<Comment> comments;
}
