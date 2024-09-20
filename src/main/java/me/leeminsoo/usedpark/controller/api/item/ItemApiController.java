package me.leeminsoo.usedpark.controller.api.item;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.domain.item.Item;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.item.AddItemRequestDTO;
import me.leeminsoo.usedpark.dto.item.UpdateItemRequestDTO;
import me.leeminsoo.usedpark.service.item.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ItemApiController {

    private final ItemService itemService;

    Map<String,Object> response;

    @PostMapping("/api/items")
    public ResponseEntity<Map<String,Object>> addItem(@RequestPart("item") @Validated AddItemRequestDTO dto,
                                          @RequestPart(value = "images",required = false) List<MultipartFile> imageFiles,
                                          @AuthenticationPrincipal User user){
        dto.setUser(user);
        Item item = itemService.save(dto,imageFiles);
        response = new HashMap<>();
        response.put("id",item.getId());
        response.put("message","상품이 정상적으로 등록되었습니다.");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/api/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable(name = "itemId") Long itemId , @AuthenticationPrincipal User user){

        itemService.delete(itemId,user);

        return ResponseEntity.ok()
                .build();
    }
    @PutMapping("/api/items/{itemId}")
    public ResponseEntity<Map<String,Object>> updatePost(@PathVariable(name = "itemId")Long itemId, @RequestPart("item") @Validated UpdateItemRequestDTO dto,
                                             @RequestPart(value = "images",required = false) List<MultipartFile> imageFiles,
                                             @AuthenticationPrincipal User user){

        Item item = itemService.update(itemId,dto,user,imageFiles);
        response = new HashMap<>();
        response.put("id",item.getId());
        response.put("message","상품이 성공적으로 수정되었습니다.");
        return ResponseEntity.ok()
                    .body(response);

    }

}
