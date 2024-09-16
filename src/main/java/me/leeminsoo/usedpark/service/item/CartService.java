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
import me.leeminsoo.usedpark.repository.item.ItemRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;


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
    public List<ItemListResponseDTO> getCartItems(Long userId){
        List<Item> items = cartRepository.findItemsByUserId(userId);
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
