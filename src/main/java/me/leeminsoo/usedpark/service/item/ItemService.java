package me.leeminsoo.usedpark.service.item;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.notpound.CategoryNotFoundException;
import me.leeminsoo.usedpark.config.error.exception.notpound.ItemNotFoundException;
import me.leeminsoo.usedpark.domain.item.Address;
import me.leeminsoo.usedpark.domain.item.Category;
import me.leeminsoo.usedpark.domain.item.Item;
import me.leeminsoo.usedpark.domain.item.ItemImage;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.item.AddItemRequestDTO;
import me.leeminsoo.usedpark.dto.item.ItemListResponseDTO;
import me.leeminsoo.usedpark.dto.item.ItemResponseDTO;
import me.leeminsoo.usedpark.dto.item.UpdateItemRequestDTO;
import me.leeminsoo.usedpark.repository.item.AddressRepository;
import me.leeminsoo.usedpark.repository.item.CategoryRepository;
import me.leeminsoo.usedpark.repository.item.ItemImageRepository;
import me.leeminsoo.usedpark.repository.item.ItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final CategoryRepository categoryRepository;
    private final AddressRepository addressRepository;


    @Value("${file.profileImagePath}")
    private String uploadFolder;

    @Transactional
    public Item save(AddItemRequestDTO dto, List<MultipartFile> imageFiles){
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("찾을수없는 카테고리입니다"));
        List<Address> addresses = addressRepository.findAllById(dto.getAddressIds());
        Item item = Item.builder().title(dto.getTitle())
                    .brand(dto.getBrand())
                    .user(dto.getUser())
                    .content(dto.getContent())
                    .price(dto.getPrice())
                    .category(category)
                    .addresses(addresses)
                    .build();
            itemRepository.save(item);
            if (!(imageFiles == null)) {
                imageSave(imageFiles, item,dto.getRepresentativeImageIndex());
            }
            return item;
    }

    @Transactional
    public void delete(Long itemId, User user){
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        if (item.getUser().getId().equals(user.getId())) {
            itemRepository.deleteById(itemId);
        }else throw new AccessDeniedException("권한이 없습니다 ");
        List<ItemImage>images = itemImageRepository.findByItemId(itemId);
        this.deleteImage(images, itemId);

    }

    public void imageSave(List<MultipartFile> images, Item item,byte representativeImageIndex) {
        if (images != null && !images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                MultipartFile file = images.get(i);
                if (!isValidImageFile(file)) {
                    throw new IllegalArgumentException("PNG 또는 JPEG 확장자 파일만 업로드 가능합니다");
                }

                UUID uuid = UUID.randomUUID();
                String imageFileName = uuid + "_" + file.getOriginalFilename();
                File destinationFile = new File(uploadFolder + imageFileName);

                try {
                    file.transferTo(destinationFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                boolean representative = (i == representativeImageIndex);

                ItemImage image = ItemImage.builder()
                        .url("/itemImage/" + imageFileName)
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
            Path filePath = Paths.get(uploadFolder, fileName);

            File file = filePath.toFile();
            itemImageRepository.deleteByItemId(itemId);
            if (file.exists()) {
                file.delete();
            }
        }

    }
    @Transactional
    public Item update(Long itemId, UpdateItemRequestDTO dto,User user, List<MultipartFile> imageFiles) {
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(CategoryNotFoundException::new);
        List<Address> addresses = addressRepository.findAllById(dto.getAddressIds());

        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        if(item.getUser().getId().equals(user.getId())){
            item.update(dto.getTitle(),dto.getBrand(),dto.getContent(),dto.getPrice(),category,addresses);
        }else throw new AccessDeniedException("권한이 없습니다");
        List<ItemImage> images = itemImageRepository.findByItemId(itemId);
        this.deleteImage(images,itemId);
        if (!(imageFiles == null)) {
            imageSave(imageFiles, item, dto.getRepresentativeImageIndex());
        }
        return item;
    }
    public Page<ItemListResponseDTO> getItems(String order, int page, int size,Long addressId,Long categoryId){
        Pageable pageable = setPageable(order,page,size);
        Page<Item> items;
        if (addressId != null){
            items = itemRepository.findByAddresses_Id(addressId,pageable);
        }else if (categoryId != null){
            items = itemRepository.findByCategoryId(categoryId,pageable);
        }else {
            items = itemRepository.findAll(pageable);
        }
        return items.map(item -> {
            int cartCount = item.getCarts().size();
            ItemImage representativeImage = item.getImages().stream()
                    .filter(ItemImage::isRepresentative)
                    .findFirst()
                    .orElse(null);
            return new ItemListResponseDTO(item,cartCount,representativeImage);
        });
    }

    public Page<ItemListResponseDTO> searchItems(String order,int page, int size, String keyword){
        Pageable pageable = setPageable(order,page,size);
        Page<Item> items = itemRepository.findByTitleContaining(keyword,pageable);

        return items.map(item -> {
            int cartCount = item.getCarts().size();
            ItemImage representativeImage = item.getImages().stream()
                    .filter(ItemImage::isRepresentative)
                    .findFirst()
                    .orElse(null);
            return new ItemListResponseDTO(item,cartCount,representativeImage);
        });
    }

    public Pageable setPageable(String order, int page, int size) {
        if (size < 1) {
            size = 10;
        }
        Sort sort = Sort.by(Sort.Direction.fromString(order), "id");
        return PageRequest.of(page, size, sort);
    }

    public Item findById(Long itemId){
        return itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
    }

    public ItemResponseDTO getItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        int cartCount = item.getCarts().size();
        return new ItemResponseDTO(item,cartCount);
    }

    public Page<ItemListResponseDTO> getItemByUserId(Long userId,String order,int page,int size) {
        Pageable pageable = setPageable(order,page,size);
        Page<Item> items = itemRepository.findByUserId(userId,pageable);

        return items.map(item -> {
            int cartCount = item.getCarts().size();
            ItemImage representativeImage = item.getImages().stream()
                    .filter(ItemImage::isRepresentative)
                    .findFirst()
                    .orElse(null);
            return new ItemListResponseDTO(item,cartCount,representativeImage);
        });

    }
    @Transactional
    public void updateItemStatus(Long itemId, User user) {
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        if (item.getUser().getId().equals(user.getId())){
            item.itemStatusUpdate(item.isItemStatus());
        }else {
            throw new AccessDeniedException("권한이 없습니다");
        }
    }
    public List<ItemListResponseDTO> getRecentItems(){
        List<Item> items = itemRepository.findTop6ByOrderByCreatedAtDesc();
        return items.stream().map(item -> {
            int cartCount = item.getCarts().size();
            ItemImage representativeImage = item.getImages().stream()
                    .filter(ItemImage::isRepresentative)
                    .findFirst()
                    .orElse(null);
            return new ItemListResponseDTO(item,cartCount,representativeImage);
        }).toList();
    }
}
