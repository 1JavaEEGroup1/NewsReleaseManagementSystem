package com.example.newsreleasemanagementsystem.service;

import com.example.newsreleasemanagementsystem.util.ResponseResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CommonService {
    /**
     * 上传文件接口
     *
     * @param multipartFile
     * @param path
     * @return
     */
    ResponseResult<?> uploadFile(MultipartFile multipartFile, String path);
}
