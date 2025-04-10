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

    private String getKeyFromUrl(final String url) {
        final String originUrl = getPublicUrl("");
        if (!url.startsWith(originUrl)) {
            return null;
        }
        return url.substring(originUrl.length());
    }

    private String getPublicUrl(final String fileName) {
        try {
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
            encodedFileName = encodedFileName.replace("+", "%20");
            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), encodedFileName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String uploadFile(final MultipartFile file, final S3ImageCategory s3ImageCategory) {
        // filename -> uuid
        final String filename = "file/" + s3ImageCategory.toString().toLowerCase() + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
            final ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            final PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filename, file.getInputStream(), metadata);
            amazonS3.putObject(putObjectRequest);
            return getPublicUrl(filename);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> uploadFiles(final List<MultipartFile> files, final S3ImageCategory s3ImageCategory) {
        final List<String> result = new ArrayList<>();
        files.forEach(file -> {
            String filename = "file/" + s3ImageCategory.toString().toLowerCase() + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            try {
                final ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                metadata.setContentType(file.getContentType());
                final PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filename, file.getInputStream(), metadata);
                amazonS3.putObject(putObjectRequest);
                result.add(getPublicUrl(filename));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
        return result;
    }

    public String transferFile(final String path, final S3ImageCategory s3ImageCategory) {
        final String key = getKeyFromUrl(path);
        final String filename = Objects.requireNonNull(key).substring(key.indexOf("/"));
        try {
            final CopyObjectRequest copyObjectRequest =
                    new CopyObjectRequest(bucket, key, bucket, s3ImageCategory.toString().toLowerCase() + "/" + filename);
            final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
            amazonS3.copyObject(copyObjectRequest);
            amazonS3.deleteObject(deleteObjectRequest);
            return getPublicUrl(filename);
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> transferFile(final List<String> paths, final S3ImageCategory s3ImageCategory) {
        final List<String> urls = new ArrayList<>();
        for (String path : paths) {
            urls.add(transferFile(path, s3ImageCategory));
        }
        return urls;
    }
}
