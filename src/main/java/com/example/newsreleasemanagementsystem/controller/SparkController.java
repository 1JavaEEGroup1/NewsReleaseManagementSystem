package com.example.newsreleasemanagementsystem.controller;

import com.example.newsreleasemanagementsystem.spark.Service.SparkService;
import com.example.newsreleasemanagementsystem.spark.Service.impl.SparkServiceImlpl;
import com.example.newsreleasemanagementsystem.util.ResponseResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/spark")
public class SparkController {
    private SparkService sparkService;
    public SparkController(SparkService sparkService) {
        this.sparkService = sparkService;
        SparkServiceImlpl.caculate();
    }
    @GetMapping("/productForOne")
    public ResponseResult<?> recommendProductForOne(@RequestParam Integer userId, @RequestParam Integer num) {
       return sparkService.recommendProductForOne(userId, num);
    }
    @GetMapping("/productToOne")
    public ResponseResult<?> recommendProductToOne(@RequestParam Integer productId, @RequestParam Integer num) {
        return sparkService.recommendProductToOne(productId, num);
    }
}
