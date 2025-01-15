package com.handong.cra.crawebbackend.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.handong.cra.crawebbackend.file.domain.Image;
import com.handong.cra.crawebbackend.file.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class ImageServiceImpl implements ImageService {


    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;


    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), fileName);
    }

    @Override
    public String uploadImage(MultipartFile image) throws IOException {

        // filename -> uuid
        String filename = UUID.randomUUID().toString();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(image.getSize());
        metadata.setContentType(image.getContentType());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filename, image.getInputStream(), metadata);
        amazonS3.putObject(putObjectRequest);
        return getPublicUrl(filename);

    }

    // 사용하지 않는 데이터 제거.
    @Scheduled(cron = "0/5 * * * * ?")
    private void deletesUnUsingImgs() {
        List<Image> imgs = imageRepository.findAllIsUsingFalse();
        // s3 delete
        imageRepository.deleteAll(imgs);
    }
}
