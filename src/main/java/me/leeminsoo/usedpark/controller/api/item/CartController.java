package me.leeminsoo.usedpark.controller.api.item;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.item.CartResponse;
import me.leeminsoo.usedpark.service.item.CartService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/api/item/{itemId}/cart")
    public ResponseEntity<CartResponse> addCart(@AuthenticationPrincipal User user, @PathVariable(name = "itemId") Long itemId){
        int cartCount = cartService.addCart(user,itemId);
        CartResponse cartResponse = new CartResponse(cartCount);
        return ResponseEntity.status(HttpStatus.OK).body(cartResponse);
    }

    @DeleteMapping("/api/item/{itemId}/cart")
    public ResponseEntity<Void> deleteCart(@AuthenticationPrincipal User user,@PathVariable(name = "itemId")Long itemId){
        cartService.deleteCart(user,itemId);
        return ResponseEntity.ok().build();
    }

}
