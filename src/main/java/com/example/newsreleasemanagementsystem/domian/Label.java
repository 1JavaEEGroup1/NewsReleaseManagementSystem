package com.example.newsreleasemanagementsystem.domian;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long labelId;

    private String name;
    private Date CreateTime;

    @ManyToMany(mappedBy = "labels")
    private Set<New> news;
}
