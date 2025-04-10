package com.handong.cra.crawebbackend.file.service;

import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3ImageService {
    /**
     * 사진 업로드
     *
     * @param image 업로드할 사진
     */
    String uploadImage(MultipartFile image);

    /**
     * 사진의 용도 변경
     * <br />S3상 폴더 이동
     *
     * @param path            저장된 사진의 URL
     * @param s3ImageCategory 변경할 사진의 용도 (삭제 포함)
     */
    String transferImage(String path, S3ImageCategory s3ImageCategory);


    /**
     * 여러 사진의 용도 변경
     * <br />S3상 폴더 이동
     *
     * @param paths           저장된 사진의 URL
     * @param s3ImageCategory 변경할 사진의 용도 (삭제 포함)
     */
    List<String> transferImage(List<String> paths, S3ImageCategory s3ImageCategory);
}
