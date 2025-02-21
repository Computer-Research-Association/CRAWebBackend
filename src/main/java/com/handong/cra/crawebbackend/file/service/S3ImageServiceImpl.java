package com.handong.cra.crawebbackend.file.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.handong.cra.crawebbackend.exception.s3image.S3ImageTransferException;
import com.handong.cra.crawebbackend.exception.s3image.S3ImageUploadException;
import com.handong.cra.crawebbackend.exception.s3image.S3ImageUrlException;
import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class S3ImageServiceImpl implements S3ImageService {
    private static final Logger log = LoggerFactory.getLogger(S3ImageServiceImpl.class);
    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private String getKeyFromUrl(String url) {
        String originUrl = getPublicUrl(""); // get aws s3 url
        if (!url.contains(originUrl)) throw new S3ImageUrlException();
        else return url.substring(originUrl.length());
    }

    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), fileName);
    }

    @Override
    public String uploadImage(MultipartFile image) {
        // filename -> uuid
        String filename = "temp/" + UUID.randomUUID();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(image.getSize());
            metadata.setContentType(image.getContentType());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filename, image.getInputStream(), metadata);
            amazonS3.putObject(putObjectRequest);
            return getPublicUrl(filename);
        } catch (Exception e) {
            throw new S3ImageUploadException();
        }
    }

    public String transferImage(String path, S3ImageCategory s3ImageCategory) {
        log.info("image transfer. url = {} to {}", path, s3ImageCategory.toString());
        String key = getKeyFromUrl(path);
        log.debug("key = {}", key);
        String filename = Objects.requireNonNull(key).substring(key.indexOf("/"));
        log.debug("filename = {}", filename);

        // wrong url
        if (!path.contains(getPublicUrl(""))) throw new S3ImageUrlException();

        String newPath = s3ImageCategory.toString().toLowerCase() + filename;
        try {
            CopyObjectRequest copyObjectRequest =
                    new CopyObjectRequest(bucket, key, bucket, newPath);
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);

            amazonS3.copyObject(copyObjectRequest);
            amazonS3.deleteObject(deleteObjectRequest);
            return getPublicUrl(newPath);
        } catch (Exception e) {
            throw new S3ImageTransferException();
        }
    }

    public List<String> transferImage(List<String> paths, S3ImageCategory s3ImageCategory) {
        List<String> urls = new ArrayList<>();
        for (String path : paths)
            urls.add(transferImage(path, s3ImageCategory));
        return urls;
    }

    @Override
    public String generatePresignedURL() {
        // test
         Date expiration = new Date();
        expiration.setTime(System.currentTimeMillis() + (1000 * 60 * 5));
        String filename = "/temp/"+UUID.randomUUID().toString();

        GeneratePresignedUrlRequest generatePresignedUrlRequest
                = new GeneratePresignedUrlRequest(bucket, filename)
                .withMethod(HttpMethod.PUT)
//                .withKey("/temp" + filename)
                .withExpiration(expiration);
        log.info("{}", amazonS3.generatePresignedUrl(generatePresignedUrlRequest));
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }
}
