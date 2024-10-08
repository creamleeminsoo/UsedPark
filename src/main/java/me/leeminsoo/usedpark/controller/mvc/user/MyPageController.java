package me.leeminsoo.usedpark.controller.mvc.user;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.admin.service.AdminService;
import me.leeminsoo.usedpark.config.error.exception.UserNotAuthenticationException;
import me.leeminsoo.usedpark.domain.board.Board;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.board.post.view.PostListViewResponseDTO;
import me.leeminsoo.usedpark.dto.item.ItemListResponseDTO;
import me.leeminsoo.usedpark.service.board.PostService;
import me.leeminsoo.usedpark.service.item.CartService;
import me.leeminsoo.usedpark.service.item.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class MyPageController {

    private final CartService cartService;
    private final PostService postService;
    private final AdminService adminService;
    private final ItemService itemService;

    @GetMapping("/mypage")
    public String getMyPage(@AuthenticationPrincipal User user, Model model){
        if (user == null){
            throw new UserNotAuthenticationException();
        }
        List<ItemListResponseDTO> items = cartService.getCartItems(user.getId());
        model.addAttribute("items",items);
        model.addAttribute("user",user);
        return "mypage/mypage";
    }
    @GetMapping("/user/posts/{userId}")
    public String getMyPagePosts(@PathVariable(name = "userId") Long userId, @RequestParam(name = "boardId",defaultValue = "0") Long boardId,
                                 @RequestParam(name = "order",defaultValue = "desc")String order,
                                 @RequestParam(name = "page",defaultValue = "0")int page,
                                 @RequestParam(name = "size",defaultValue = "10")int size,Model model){
        Page<PostListViewResponseDTO> posts = postService.findByUserId(userId,boardId,page,size,order);
        List<Board> boards = adminService.findBoard();

        model.addAttribute("userId",userId);
        model.addAttribute("board",boards);
        model.addAttribute("posts",posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("totalItems", posts.getTotalElements());
        model.addAttribute("order", order);
        model.addAttribute("size", size);
        return "mypage/mypagePost";
    }
    @GetMapping("/user/items/{userId}")
    public String getMyPageItems(@PathVariable(name = "userId") Long userId,
                                 @RequestParam(name = "order",defaultValue = "desc") String order,
                                 @RequestParam(name = "page",defaultValue = "0")int page,
                                 @RequestParam(name = "size",defaultValue = "10")int size,
                                 Model model) {
        Page<ItemListResponseDTO> items = itemService.getItemByUserId(userId,order,page,size);
        model.addAttribute("userId",userId);
        model.addAttribute("items",items);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", items.getTotalPages());
        model.addAttribute("totalItems", items.getTotalElements());
        model.addAttribute("order", order);
        model.addAttribute("size", size);
        return "mypage/mypageItem";
    }
    @PatchMapping("/items/{itemId}/status") // 변경부분이 적을때 patch 메서드로
    public ResponseEntity<Void> updateItemStatus(@PathVariable(name = "itemId") Long itemId, @AuthenticationPrincipal User user){
        itemService.updateItemStatus(itemId,user);
        return ResponseEntity.ok().build();
    }
}
