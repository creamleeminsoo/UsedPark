package me.leeminsoo.usedpark.service.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        try {
            amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException("S3 파일 업로드 실패", e);
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public Resource getFile(String fileName) {
        S3Object s3Object = amazonS3.getObject(bucket, fileName);
        InputStream inputStream = s3Object.getObjectContent();
        return new InputStreamResource(inputStream);
    }

    public String getFileContentType(String fileName) {
        ObjectMetadata metadata = amazonS3.getObjectMetadata(bucket, fileName);
        return metadata.getContentType();
    }

    public void delete(String fileName) {
        try {
            amazonS3.deleteObject(bucket, fileName);
        } catch (AmazonS3Exception e) {
            System.err.println("객체 삭제중 오류발생: " + e.getMessage());
        }
    }
}
