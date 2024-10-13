package me.leeminsoo.usedpark.service.item;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.InvalidInput.CartDuplicationException;
import me.leeminsoo.usedpark.config.error.exception.UserNotAuthenticationException;
import me.leeminsoo.usedpark.config.error.exception.notpound.CartNotFoundException;
import me.leeminsoo.usedpark.config.error.exception.notpound.PostNotFoundException;
import me.leeminsoo.usedpark.domain.item.Cart;
import me.leeminsoo.usedpark.domain.item.Item;
import me.leeminsoo.usedpark.domain.item.ItemImage;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.item.ItemListResponseDTO;
import me.leeminsoo.usedpark.repository.item.CartRepository;
import me.leeminsoo.usedpark.repository.item.ItemImageRepository;
import me.leeminsoo.usedpark.repository.item.ItemRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;

    @Transactional
    public int addCart(User user, Long itemId){
        if (user == null){
            throw new UserNotAuthenticationException();
        }
        Item item = itemRepository.findById(itemId).orElseThrow(PostNotFoundException::new);

        if (!cartRepository.existsByItemAndUser(item,user)){
            cartRepository.addCart(itemId,user.getId());
            return cartRepository.countCartsByItemId(itemId);
        }else {
            throw new CartDuplicationException();
        }
    }

    @Transactional
    public void deleteCart(User user,Long itemId) {
        Cart cart = cartRepository.findByItemId(itemId).orElseThrow(CartNotFoundException::new);
        if (cart.getUser().getId().equals(user.getId())){
            cartRepository.deleteById(cart.getId());
        }else {
            throw new AccessDeniedException("권한이 없습니다");
        }
    }
//    public List<ItemListResponseDTO> getCartItems(Long userId){ // 쿼리 나눔
//        List<Item> items = cartRepository.findItemsInCartByUser(userId);
//
//        List<Long> itemIds = items.stream()
//                .map(Item::getId)
//                .toList();
//
//        List<ItemImage> representativeImagesList = itemImageRepository.findAllRepresentativeImagesByItems(itemIds);
//
//        Map<Long, ItemImage> representativeImages = representativeImagesList.stream()
//                .collect(Collectors.toMap(img -> img.getItem().getId(), img -> img));
//
//
//        return items.stream().map(item -> {
//            int cartCount = item.getCarts().size();  //패치조인사용 추가쿼리 x
//            ItemImage representativeImage = representativeImages.get(item.getId());
//            return new ItemListResponseDTO(item,cartCount,representativeImage);
//            }).toList();
//        }
    public List<ItemListResponseDTO> getCartItems(Long userId){ // 한방쿼리 사용
        return cartRepository.findItemsInCartByUser(userId);
    }




}
