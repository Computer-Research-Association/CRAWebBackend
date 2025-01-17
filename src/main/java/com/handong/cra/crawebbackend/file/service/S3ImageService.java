package com.handong.cra.crawebbackend.file.service;

import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3ImageService {

    public String uploadImage(MultipartFile image);
    public String transferImage(String path, S3ImageCategory s3ImageCategory);
    public List<String> transferImage(List<String> paths, S3ImageCategory s3ImageCategory);
}
