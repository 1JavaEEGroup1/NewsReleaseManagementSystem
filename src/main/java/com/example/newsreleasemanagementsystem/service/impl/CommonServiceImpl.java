package com.example.newsreleasemanagementsystem.service.impl;

import com.example.newsreleasemanagementsystem.config.CommonProps;
import com.example.newsreleasemanagementsystem.service.CommonService;
import com.example.newsreleasemanagementsystem.util.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xbib.io.ftp.client.FTP;
import org.xbib.io.ftp.client.FTPClient;
import org.xbib.io.ftp.client.FTPReply;

import java.util.UUID;

@Service
public class CommonServiceImpl implements CommonService {
    private CommonProps commonProps;

    public CommonServiceImpl(CommonProps commonProps) {
        this.commonProps = commonProps;
    }

    /**
     * 上传文件方法实现
     * 首先连接FTP，
     * 再将要上传的文件二进制话，
     * 再返回上传的地址
     *
     * @param multipartFile
     * @param path
     * @return
     */
    @Override
    public ResponseResult<?> uploadFile(MultipartFile multipartFile, String path) {
        String fileName = multipartFile.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + suffixName;

        int port = Integer.parseInt(commonProps.getFtpPort());
        boolean result = false;
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.enterLocalPassiveMode();
            int reply;
            ftpClient.connect(commonProps.getFtpHost(), port);

            System.out.println(ftpClient.login(commonProps.getFtpUserName(), commonProps.getFtpPassword()));
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return ResponseResult.fail(String.valueOf(result));
            }

            ftpClient.changeWorkingDirectory(path);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.storeFile(fileName, multipartFile.getInputStream());

        } catch (Exception e) {
            return ResponseResult.success(e.toString());
        }

        return ResponseResult.success(commonProps.getNginxPath() + path + "/" + fileName);
    }

}
