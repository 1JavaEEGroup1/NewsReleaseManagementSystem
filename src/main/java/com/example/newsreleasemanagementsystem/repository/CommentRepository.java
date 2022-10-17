package com.example.newsreleasemanagementsystem.repository;

import com.example.newsreleasemanagementsystem.domian.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jhlyh
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
