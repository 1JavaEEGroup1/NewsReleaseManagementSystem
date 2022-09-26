package com.example.newsreleasemanagementsystem.domian;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author jhlyh
 */
@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE,force = true)
public class User {
    @Id
    private final Long userId;

    private String name;
    private String password;
    private String email;
    private String img;
    private Integer userType;
    private Date createTime;
    private Date freshTime;
}
