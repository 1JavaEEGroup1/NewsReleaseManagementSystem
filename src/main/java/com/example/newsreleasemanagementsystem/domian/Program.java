package com.example.newsreleasemanagementsystem.domian;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author jhlyh
 */
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class Program {
    @Id
    private final Long programId;

    @ManyToOne(targetEntity = User.class)
    private final User user;

    @ManyToOne(targetEntity = Program.class)
    private final Program father;

    private String name;
    private Date createId;
    private Date freshId;
}
