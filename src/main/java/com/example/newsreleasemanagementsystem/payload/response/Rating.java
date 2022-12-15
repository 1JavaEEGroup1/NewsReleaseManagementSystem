package com.example.newsreleasemanagementsystem.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rating {
    private Integer userId;
    private Integer newId;
    private Double rating;
}
