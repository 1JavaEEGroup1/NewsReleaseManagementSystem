package com.example.newsreleasemanagementsystem.spark.Service.impl;

import com.example.newsreleasemanagementsystem.config.SpringUtil;
import com.example.newsreleasemanagementsystem.spark.Service.SparkService;
import com.example.newsreleasemanagementsystem.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.rdd.RDD;
import org.springframework.stereotype.Service;
import scala.Serializable;
import scala.Tuple2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SparkServiceImlpl implements SparkService , Serializable {
    private static JavaSparkContext javaSparkContext = SpringUtil.getBean(JavaSparkContext.class);
    private static MatrixFactorizationModel model;
    private static List<String> file;

    public static void caculate() {
        Path path = Paths.get("E:\\News\\NewsReleaseManagementSystem\\src\\main\\resources\\data.csv");
        try {
            List<String > file = Files.readAllLines(path);
            SparkServiceImlpl.file = file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaRDD<String> csvFile = javaSparkContext.parallelize(file);
        // 映射
        RDD<Rating> ratingRDD = csvFile.map(new Function<String, Rating>() {
            public Rating call(String line) throws Exception {
                String[] arr = line.split(",");
                return new Rating(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Double.parseDouble(arr[2]));
            }
        }).rdd();
        MatrixFactorizationModel model = ALS.train(ratingRDD, 10, 10);
        SparkServiceImlpl.model = model;
    }

    @Override
    public ResponseResult<?> recommendProductForOne(Integer userId, Integer num) {
        Rating[] Returns = model.recommendProducts(userId, num);
        List<com.example.newsreleasemanagementsystem.payload.response.Rating> returns = new ArrayList<>();
        for(Rating rating : Returns) {
            returns.add(new com.example.newsreleasemanagementsystem.payload.response.Rating(rating.user(), rating.product(), rating.rating()));
        }
        return ResponseResult.success(returns);
    }

    @Override
    public ResponseResult<?> recommendProductToOne(Integer productId, Integer num) {
        Rating[] Returns = model.recommendUsers(productId, num);
        List<com.example.newsreleasemanagementsystem.payload.response.Rating> returns = new ArrayList<>();
        for(Rating rating : Returns) {
            returns.add(new com.example.newsreleasemanagementsystem.payload.response.Rating(rating.user(), rating.product(), rating.rating()));
        }
        return ResponseResult.success(returns);
    }

    @Override
    public ResponseResult<?> recommendSomeProductForAll(Integer num) {
        RDD<Tuple2<Object, Rating[]>> predictProductsForUsers = model.recommendProductsForUsers(num);
        List<com.example.newsreleasemanagementsystem.payload.response.Rating> returns = new ArrayList<>();
        predictProductsForUsers.toJavaRDD().foreach(new VoidFunction<Tuple2<Object, Rating[]>>() {
            public void call(Tuple2<Object, Rating[]> tuple2) throws Exception {
                for(Rating rating:tuple2._2){
                    returns.add(new com.example.newsreleasemanagementsystem.payload.response.Rating(rating.user(), rating.product(), rating.rating()));
                }
            }
        });
        return ResponseResult.success(returns);
    }

    @Override
    public ResponseResult<?> recommendAllProductToSome(Integer num) {
         RDD<Tuple2<Object, Rating[]>> predictUsersForProducts = model.recommendUsersForProducts(num);
        List<com.example.newsreleasemanagementsystem.payload.response.Rating> returns = new ArrayList<>();
        predictUsersForProducts.toJavaRDD().foreach(new VoidFunction<Tuple2<Object, Rating[]>>() {
            public void call(Tuple2<Object, Rating[]> tuple2) throws Exception {
                for(Rating rating:tuple2._2){
                    returns.add(new com.example.newsreleasemanagementsystem.payload.response.Rating(rating.user(), rating.product(), rating.rating()));
                }
            }
        });
        return ResponseResult.success(returns);
    }
}
