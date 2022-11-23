package com.example.newsreleasemanagementsystem.repository;

import com.example.newsreleasemanagementsystem.domian.New;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewRepository extends JpaRepository<New, Long> {
}
