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
import me.leeminsoo.usedpark.service.file.S3Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final CategoryRepository categoryRepository;
    private final AddressRepository addressRepository;
    private final S3Service s3Service;



    @Transactional
    public Item save(AddItemRequestDTO dto, List<MultipartFile> imageFiles){
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("찾을수없는 카테고리입니다"));
        Address address = addressRepository.findById(dto.getAddressId()).orElseThrow(() -> new IllegalArgumentException("찾을수 없는 주소입니다"));
        Item item = Item.builder().title(dto.getTitle())
                    .brand(dto.getBrand())
                    .user(dto.getUser())
                    .content(dto.getContent())
                    .price(dto.getPrice())
                    .category(category)
                    .address(address)
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
        List<ItemImage>images = itemImageRepository.findByItemId(itemId);
        if (item.getUser().getId().equals(user.getId())) {
            this.deleteImage(images, itemId);
            itemRepository.deleteById(itemId);
        }else throw new AccessDeniedException("권한이 없습니다 ");
    }

    public void imageSave(List<MultipartFile> images, Item item,byte representativeImageIndex) {
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
    @Transactional
    public Item update(Long itemId, UpdateItemRequestDTO dto,User user, List<MultipartFile> imageFiles) {
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(CategoryNotFoundException::new);
        Address address = addressRepository.findById(dto.getAddressId()).orElseThrow(() -> new IllegalArgumentException("주소를 찾을수 없습니다"));
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        List<ItemImage> images = itemImageRepository.findByItemId(itemId);
        if(item.getUser().getId().equals(user.getId())){
            this.deleteImage(images,itemId);
            item.update(dto.getTitle(),dto.getBrand(),dto.getContent(),dto.getPrice(),category,address);
        }else throw new AccessDeniedException("권한이 없습니다");
        if (!(imageFiles == null)) {
            imageSave(imageFiles, item, dto.getRepresentativeImageIndex());
        }
        return item;
    }

    public Page<ItemListResponseDTO> getItems(String order, int page, int size, Long addressId, Long categoryId) {
        Pageable pageable = setPageable(order, page, size);
        Page<ItemListResponseDTO> items;
        if (addressId != null) {
            items = itemRepository.findByAddressWithRepresentativeImage(addressId, pageable);
        } else if (categoryId != null) {
            items = itemRepository.findByCategoryWithRepresentativeImage(categoryId, pageable);
        } else {
            items = itemRepository.findAllWithRepresentativeImage(pageable);
        }
        return items;
    }


    public Page<ItemListResponseDTO> searchItems(String order,int page, int size, String keyword){
        Pageable pageable = setPageable(order,page,size);
        Page<Item> items = itemRepository.findByTitleContaining(keyword,pageable);

        return items.map(item -> {
            int cartCount = item.getCarts().size();
            ItemImage representativeImage = itemImageRepository.findByItemIdAndIsRepresentativeTrue(item.getId());
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
        return itemRepository.findAllWithRepresentativeImageByUserId(userId,pageable);
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

        Pageable pageable = PageRequest.of(0,6);
        return itemRepository.findTop6RecentItems(pageable);
    }
}
