package com.handong.cra.crawebbackend.test;

import com.handong.cra.crawebbackend.file.domain.S3ImageCategory;
import com.handong.cra.crawebbackend.file.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/test/s3")
@RequiredArgsConstructor
public class TestFileController {

    private final S3FileService s3FileService;

    // 파일 업로드 엔드포인트
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("category") S3ImageCategory category) {
        String url = s3FileService.uploadFile(file, category);
        if (url != null) {
            return ResponseEntity.ok(url);
        } else {
            return ResponseEntity.status(500).body("File upload failed");
        }
    }

    // 파일 이동 엔드포인트
    @PostMapping("/transfer")
    public ResponseEntity<Object> transferFile(@RequestBody TransferRequest request) {
        List<String> urls = s3FileService.transferFile(request.getPaths(), request.getCategory());
        return ResponseEntity.ok(urls);
    }

    // 파일 이동 요청을 위한 DTO 클래스
    public static class TransferRequest {
        private List<String> paths;
        private S3ImageCategory category;

        public List<String> getPaths() {
            return paths;
        }

        public void setPaths(List<String> paths) {
            this.paths = paths;
        }

        public S3ImageCategory getCategory() {
            return category;
        }

        public void setCategory(S3ImageCategory category) {
            this.category = category;
        }
    }
}