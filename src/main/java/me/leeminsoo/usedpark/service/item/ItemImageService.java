package me.leeminsoo.usedpark.service.item;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.domain.item.Item;
import me.leeminsoo.usedpark.domain.item.ItemImage;
import me.leeminsoo.usedpark.repository.item.ItemImageRepository;
import me.leeminsoo.usedpark.service.file.S3Service;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;
    private final S3Service s3Service;

    public void imageSave(List<MultipartFile> images, Item item, byte representativeImageIndex) {
        if (images != null && !images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                MultipartFile file = images.get(i);
                if (!isValidImageFile(file)) {
                    throw new IllegalArgumentException("PNG 또는 JPEG 확장자 파일만 업로드 가능합니다");
                }
                String imageUrl = s3Service.upload(file);
                boolean representative = (i == representativeImageIndex);

                ItemImage image = ItemImage.builder()
                        .url(imageUrl)
                        .item(item)
                        .isRepresentative(representative)
                        .build();

                itemImageRepository.save(image);
            }
        }
    }
    public boolean isValidImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType.equals(MediaType.IMAGE_JPEG_VALUE) || contentType.equals(MediaType.IMAGE_PNG_VALUE);
    }

    public void deleteImage(List<ItemImage> images,Long itemId) {
        for (ItemImage image : images) {
            String imagePath = image.getUrl();
            String fileName = imagePath.substring(imagePath.lastIndexOf('/') + 1);

            itemImageRepository.deleteByItemId(itemId);
            s3Service.delete(fileName);
        }
    }
    public List<ItemImage> findItemImageByItemId(Long itemId){
        return itemImageRepository.findByItemId(itemId);
    }
}
