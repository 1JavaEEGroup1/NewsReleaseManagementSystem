package com.example.newsreleasemanagementsystem.repository;

import com.example.newsreleasemanagementsystem.domian.New;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author jhlyh
 */
public interface NewRepository extends PagingAndSortingRepository<New,Long> {
}
