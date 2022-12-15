package com.example.newsreleasemanagementsystem.spark.Service;

import com.example.newsreleasemanagementsystem.util.ResponseResult;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;

public interface SparkService {
    ResponseResult<?> recommendProductForOne(Integer userId, Integer num);
    ResponseResult<?> recommendProductToOne(Integer productId, Integer num);
    ResponseResult<?> recommendSomeProductForAll(Integer num);
    ResponseResult<?> recommendAllProductToSome(Integer num);
}
