package me.leeminsoo.usedpark.service.board;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.domain.board.PostImage;
import me.leeminsoo.usedpark.repository.board.ImageRepository;
import me.leeminsoo.usedpark.service.file.S3Service;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostImageService {
    private final S3Service s3Service;
    private final ImageRepository imageRepository;

    public void imageSave(List<MultipartFile> images, Post post) {
        if (images != null && !images.isEmpty()) {
            for (MultipartFile file : images) {
                if (!isValidImageFile(file)) {
                    throw new IllegalArgumentException("PNG 또는 JPEG 파일만 업로드 가능합니다");
                }

                String imageUrl = s3Service.upload(file);

                PostImage postImage = PostImage.builder()
                        .url(imageUrl)
                        .post(post)
                        .build();

                imageRepository.save(postImage);
            }
        }
    }
    public boolean isValidImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType.equals(MediaType.IMAGE_JPEG_VALUE) || contentType.equals(MediaType.IMAGE_PNG_VALUE);
    }

    public void deleteImage(List<PostImage> images, Long postId) {
        for (PostImage image : images) {
            String imagePath = image.getUrl();
            String fileName = imagePath.substring(imagePath.lastIndexOf('/') + 1);

            imageRepository.deleteByPostId(postId);
            s3Service.delete(fileName);
        }
    }
    public List<PostImage> findImagesByPostId(Long postId) {
        return imageRepository.findByPostId(postId);
    }
}
