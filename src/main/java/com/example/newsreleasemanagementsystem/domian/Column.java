package com.example.newsreleasemanagementsystem.domian;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author jhlyh
 */
@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Column {
    @Id
    private final Long columnId;

    @ManyToOne(targetEntity = User.class)
    private final Long authorId;

    @ManyToOne(targetEntity = Column.class)
    private final Long fatherId;

    private String name;
    private Date createId;
    private Date freshId;
}
