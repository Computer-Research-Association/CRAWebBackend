package com.handong.cra.crawebbackend.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.handong.cra.crawebbackend.exception.s3image.S3ImageTransferException;
import com.handong.cra.crawebbackend.exception.s3image.S3ImageUploadException;
import com.handong.cra.crawebbackend.exception.s3image.S3ImageUrlException;
import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ImageServiceImpl implements S3ImageService {
    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private String getKeyFromUrl(final String url) {
        final String originUrl = getPublicUrl(""); // get aws s3 url
        if (!url.contains(originUrl)) {
            throw new S3ImageUrlException();
        }
        return url.substring(originUrl.length());
    }

    private String getPublicUrl(final String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), fileName);
    }

    @Override
    public String uploadImage(final MultipartFile image) {
        // filename -> uuid
        final String filename = "temp/" + UUID.randomUUID();
        try {
            final ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(image.getSize());
            metadata.setContentType(image.getContentType());
            final PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filename, image.getInputStream(), metadata);
            amazonS3.putObject(putObjectRequest);
            return getPublicUrl(filename);
        } catch (Exception e) {
            throw new S3ImageUploadException();
        }
    }

    public String transferImage(final String path, final S3ImageCategory s3ImageCategory) {
        final String key = getKeyFromUrl(path);
        final String filename = Objects.requireNonNull(key).substring(key.indexOf("/"));

        if (!path.contains(getPublicUrl(""))) { // wrong url
            throw new S3ImageUrlException();
        }
        final String newPath = s3ImageCategory.toString().toLowerCase() + filename;
        try {
            final CopyObjectRequest copyObjectRequest =
                    new CopyObjectRequest(bucket, key, bucket, newPath);
            final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
            amazonS3.copyObject(copyObjectRequest);
            amazonS3.deleteObject(deleteObjectRequest);
            return getPublicUrl(newPath);
        } catch (Exception e) {
            throw new S3ImageTransferException();
        }
    }

    public List<String> transferImage(final List<String> paths, final S3ImageCategory s3ImageCategory) {
        final List<String> urls = new ArrayList<>();
        for (String path : paths) {
            urls.add(transferImage(path, s3ImageCategory));
        }
        return urls;
    }
}
