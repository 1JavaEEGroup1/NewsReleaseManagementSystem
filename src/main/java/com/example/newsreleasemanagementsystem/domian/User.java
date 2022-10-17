package com.example.newsreleasemanagementsystem.domian;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "author")
    private Set<New> newSet;
}
