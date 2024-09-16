package me.leeminsoo.usedpark.controller.api.item;

import lombok.RequiredArgsConstructor;
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

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemApiController {

    private final ItemService itemService;

    @PostMapping("/api/items")
    public ResponseEntity<String> addItem(@RequestPart("item") @Validated AddItemRequestDTO dto,
                                          @RequestPart(value = "images",required = false) List<MultipartFile> imageFiles,
                                          @AuthenticationPrincipal User user){
        dto.setUser(user);

       itemService.save(dto,imageFiles);
       return ResponseEntity.status(HttpStatus.CREATED)
               .body("생성 성공");
    }

    @DeleteMapping("/api/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable(name = "itemId") Long itemId , @AuthenticationPrincipal User user){

        itemService.delete(itemId,user);

        return ResponseEntity.ok()
                .build();
    }
    @PutMapping("/api/items/{itemId}")
    public ResponseEntity<String> updatePost(@PathVariable(name = "itemId")Long itemId, @RequestPart("item") @Validated UpdateItemRequestDTO dto,
                                             @RequestPart(value = "images",required = false) List<MultipartFile> imageFiles,
                                             @AuthenticationPrincipal User user){

        itemService.update(itemId,dto,user,imageFiles);
        return ResponseEntity.ok()
                    .body("수정 성공");

    }

}
