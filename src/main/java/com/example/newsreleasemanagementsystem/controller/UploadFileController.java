package com.example.newsreleasemanagementsystem.controller;

import com.example.newsreleasemanagementsystem.config.CommonProps;
import com.example.newsreleasemanagementsystem.service.CommonService;
import com.example.newsreleasemanagementsystem.util.ResponseResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/common")
public class UploadFileController {
    private CommonProps commonProps;
    private CommonService commonDetailImpl;

    public UploadFileController(CommonService commonDetailImp, CommonProps commonProps) {
        this.commonDetailImpl = commonDetailImp;
        this.commonProps = commonProps;
    }

    @PostMapping("/uploadImage")
    public ResponseResult<?> uploadImage(@RequestBody MultipartFile file) {
        return commonDetailImpl.uploadFile(file, commonProps.getFtpImagePathName());
    }

    @PostMapping("/uploadVideo")
    public ResponseResult<?> uploadVideo(@RequestBody MultipartFile file) {
        return commonDetailImpl.uploadFile(file, commonProps.getFtpVideoPathName());
    }
}
