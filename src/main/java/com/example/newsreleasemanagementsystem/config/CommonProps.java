package com.example.newsreleasemanagementsystem.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CommonProps {
    private String ftpPort = "21";
    private String ftpHost = "114.132.251.197";
    private String ftpUserName = "root";
    private String ftpPassword = "chCZD5hNS4wH7tL2";
    private String ftpImagePathName = "/images";
    private String ftpVideoPathName = "/videos";
    private String nginxPath = "http://114.132.251.197:888/";
}
