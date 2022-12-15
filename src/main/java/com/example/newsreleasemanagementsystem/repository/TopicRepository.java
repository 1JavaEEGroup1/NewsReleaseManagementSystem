package com.example.newsreleasemanagementsystem.repository;

import com.example.newsreleasemanagementsystem.domian.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
