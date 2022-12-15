package com.example.newsreleasemanagementsystem.repository;

import com.example.newsreleasemanagementsystem.domian.EState;
import com.example.newsreleasemanagementsystem.domian.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByName(EState name);
}
