package com.handong.cra.crawebbackend.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileServiceImpl implements S3FileService {
    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private String getKeyFromUrl(String url) {
        String originUrl = getPublicUrl("");
        if (!url.startsWith(originUrl)) return null;
        else return url.substring(originUrl.length());
    }

    private String getPublicUrl(String fileName) {
        try {
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
            encodedFileName = encodedFileName.replace("+", "%20");

            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), encodedFileName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String uploadFile(MultipartFile file, S3ImageCategory s3ImageCategory) {
        // filename -> uuid
        String filename = "file/" + s3ImageCategory.toString().toLowerCase() + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filename, file.getInputStream(), metadata);
            amazonS3.putObject(putObjectRequest);
            return getPublicUrl(filename);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> uploadFiles(List<MultipartFile> files, S3ImageCategory s3ImageCategory) {
        List<String> result = new ArrayList<>();
        files.forEach(file -> {
            String filename = "file/" + s3ImageCategory.toString().toLowerCase() + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            try {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                metadata.setContentType(file.getContentType());
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filename, file.getInputStream(), metadata);
                amazonS3.putObject(putObjectRequest);
                result.add(getPublicUrl(filename));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });

        return result;
    }

    public String transferFile(String path, S3ImageCategory s3ImageCategory) {
        String key = getKeyFromUrl(path);
        log.info("key = {}", key);
        String filename = Objects.requireNonNull(key).substring(key.indexOf("/"));
        log.info("filename = {}", filename);

        try {
            CopyObjectRequest copyObjectRequest =
                    new CopyObjectRequest(bucket, key, bucket, s3ImageCategory.toString().toLowerCase() + "/" + filename);
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);

            amazonS3.copyObject(copyObjectRequest);
            amazonS3.deleteObject(deleteObjectRequest);
            return   getPublicUrl(filename);
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> transferFile(List<String> paths, S3ImageCategory s3ImageCategory) {
        List<String> urls = new ArrayList<>();
        for (String path : paths)
            urls.add(transferFile(path, s3ImageCategory));
        return urls;
    }

//
//    public Boolean deleteImageById(Long id) {
//        S3Image s3Image = s3ImageRepository.findById(id).orElseThrow(() -> new RuntimeException("데이터가 없어요"));
//        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, s3Image.getKey());
//        amazonS3.deleteObject(deleteObjectRequest);
//        s3ImageRepository.deleteById(id);
//        return true;
//    }


}
