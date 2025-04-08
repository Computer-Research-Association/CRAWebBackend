package com.handong.cra.crawebbackend.file.service;

import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3FileService {

    /**
     * 파일 업로드
     *
     * @param file            업로드할 파일
     * @param s3ImageCategory 업로드할 파일의 활용 용도 (카테고리)
     */
    String uploadFile(MultipartFile file, S3ImageCategory s3ImageCategory);

    /**
     * 야러 파일 업로드
     *
     * @param files           업로드할 파일 리스트
     * @param s3ImageCategory 업로드할 파일의 활용 용도 (카테고리)
     */
    List<String> uploadFiles(List<MultipartFile> files, S3ImageCategory s3ImageCategory);

    /**
     * 파일의 용도 변경
     * <br />S3상 폴더 이동
     *
     * @param path            저장된 파일의 URL
     * @param s3ImageCategory 변경할 파일의 용도 (삭제 포함)
     */
    String transferFile(String path, S3ImageCategory s3ImageCategory);

    /**
     * 파일의 용도 변경
     * <br />S3상 폴더 이동
     *
     * @param paths           저장된 파일의 여러 URL
     * @param s3ImageCategory 변경할 파일의 용도 (삭제 포함)
     */
    List<String> transferFile(List<String> paths, S3ImageCategory s3ImageCategory);
}
