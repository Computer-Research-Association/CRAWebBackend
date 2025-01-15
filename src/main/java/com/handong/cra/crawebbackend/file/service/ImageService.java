package com.handong.cra.crawebbackend.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    public String uploadImage(MultipartFile image);
    public Boolean deleteImageById(String id);

    public Boolean saveBoardImage (String fileName);
    public Boolean deleteBoardImageById(String id);


}
