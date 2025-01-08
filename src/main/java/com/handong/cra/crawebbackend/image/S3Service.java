//package com.handong.cra.crawebbackend.image;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Service
//public class S3Service {
//    private final AmazonS3 amazonS3;
//
//    @Value("${spring.cloud.aws.s3.bucket}")
//    private String bucket;
//
//    public S3Service(AmazonS3 amazonS3) {
//        this.amazonS3 = amazonS3;
//    }
//
//    public String uploadImage(MultipartFile image) throws IOException {
//        String filename =  UUID.randomUUID() + "_" + image.getOriginalFilename();
//
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(image.getSize());
//        metadata.setContentType(image.getContentType());
//
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filename, image.getInputStream(), metadata);
//
//        amazonS3.putObject(putObjectRequest);
//        return getPublicUrl(filename);
//    }
//
//    private String getPublicUrl(String fileName) {
//        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), fileName);
//    }
//}
