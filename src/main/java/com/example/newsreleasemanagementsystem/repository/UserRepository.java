package com.example.newsreleasemanagementsystem.repository;

import com.example.newsreleasemanagementsystem.domian.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author jhlyh
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}
