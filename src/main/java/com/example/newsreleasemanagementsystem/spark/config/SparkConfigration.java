package com.example.newsreleasemanagementsystem.spark.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class SparkConfigration {
    private String sparkHome = ".";

    private String appName = "sparkPatrol";

    private String master = "local";
    @Bean
    @ConditionalOnMissingBean(SparkConf.class)
    public SparkConf sparkConf() {
        SparkConf config = new SparkConf().setAppName("jhl").setMaster("local");
        return config;
    }

    @Bean
    @ConditionalOnMissingBean(JavaSparkContext.class)
    public JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkConf());
    }
}
