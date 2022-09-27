package com.example.newsreleasemanagementsystem.repository;

import com.example.newsreleasemanagementsystem.domian.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author jhlyh
 */
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
}
