package com.example.newsreleasemanagementsystem.domian;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jhlyh
 */
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE,force = true)
@RequiredArgsConstructor
public class HeadlineNew {
    @Id
    private final Long headlineId;

    @OneToOne(targetEntity = New.class)
    private final Long newsId;

    private BigDecimal score;
    private Date releaseTime;
    private Date freshTime;
}
