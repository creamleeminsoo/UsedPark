package me.leeminsoo.usedpark.service.item;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import me.leeminsoo.usedpark.repository.item.ItemImageRepository;
import me.leeminsoo.usedpark.repository.item.ItemRepository;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryService categoryService;
    private final AddressService addressService;
    private final ItemImageService itemImageService;
    private final ItemImageRepository itemImageRepository;

    @Autowired
    private EntityManager entityManager;



    @Transactional
    public Item save(AddItemRequestDTO dto, List<MultipartFile> imageFiles){
        Category category = categoryService.findCategory(dto.getCategoryId());
        Address address = addressService.findAddress(dto.getAddressId());
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
                itemImageService.imageSave(imageFiles, item,dto.getRepresentativeImageIndex());
            }
            return item;
    }

    @Transactional
    public void delete(Long itemId, User user){
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        List<ItemImage>images = itemImageService.findItemImageByItemId(itemId);
        if (item.getUser().getId().equals(user.getId())) {
            itemImageService.deleteImage(images, itemId);
            itemRepository.deleteById(itemId);
        }else throw new AccessDeniedException("권한이 없습니다 ");
    }

    @Transactional
    public Item update(Long itemId, UpdateItemRequestDTO dto,User user, List<MultipartFile> imageFiles) {
        Category category = categoryService.findCategory(dto.getCategoryId());
        Address address = addressService.findAddress(dto.getAddressId());
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        List<ItemImage> images = itemImageService.findItemImageByItemId(itemId);
        if(item.getUser().getId().equals(user.getId())){
            itemImageService.deleteImage(images,itemId);
            item.update(dto.getTitle(),dto.getBrand(),dto.getContent(),dto.getPrice(),category,address);
        }else throw new AccessDeniedException("권한이 없습니다");
        if (!(imageFiles == null)) {
            itemImageService.imageSave(imageFiles, item, dto.getRepresentativeImageIndex());
        }
        return item;
    }

    public Page<ItemListResponseDTO> getItems(String order, int page, int size, Long addressId, Long categoryId) {
        SessionFactory sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
        Statistics statistics = sessionFactory.getStatistics();
        statistics.clear();
        System.out.println("******상품 리스트를 조회할때 쿼리 ******");

        Pageable pageable = setPageable(order, page, size);
        Page<ItemListResponseDTO> items;
        if (addressId != null) {
            items = itemRepository.findByAddressWithRepresentativeImage(addressId, pageable);
        } else if (categoryId != null) {
            items = itemRepository.findByCategoryWithRepresentativeImage(categoryId, pageable);
        } else {
            items = itemRepository.findAllWithRepresentativeImage(pageable);
        }

        long queryCount = statistics.getPrepareStatementCount();
        System.out.println("상품리스트를 조회할시 쿼리" + queryCount);

        return items;
    }

    public Page<ItemListResponseDTO> searchItems(String order,int page, int size, String keyword){
        Pageable pageable = setPageable(order,page,size);
        return itemRepository.findByTitleContaining(keyword,pageable);
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
    public Item findItem(Long itemId){
        return itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
    }

}
