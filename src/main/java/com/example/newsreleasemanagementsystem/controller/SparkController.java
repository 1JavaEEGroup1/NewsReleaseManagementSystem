package com.example.newsreleasemanagementsystem.controller;

import com.example.newsreleasemanagementsystem.spark.Spark;
import com.example.newsreleasemanagementsystem.util.ResponseResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/spark")
public class SparkController {
    @GetMapping
    public ResponseResult<?> recommend(@RequestParam Long userId) {
        return ResponseResult.success(Spark.spark(userId));
    }
}
