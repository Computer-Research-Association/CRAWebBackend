package com.handong.cra.crawebbackend.file.service;

import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import org.springframework.web.multipart.MultipartFile;

public interface S3ImageService {

    public String uploadImage(MultipartFile image, S3ImageCategory s3ImageCategory);
    public Boolean transferImage(String path, S3ImageCategory s3ImageCategory);
}
