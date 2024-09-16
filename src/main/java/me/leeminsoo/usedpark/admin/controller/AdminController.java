package me.leeminsoo.usedpark.admin.controller;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.admin.dto.AddBoardRequestDTO;
import me.leeminsoo.usedpark.admin.dto.AdminViewResponseDTO;
import me.leeminsoo.usedpark.admin.dto.UserListViewResponseDTO;
import me.leeminsoo.usedpark.admin.service.AdminService;
import me.leeminsoo.usedpark.domain.board.Board;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.board.post.view.PostListViewResponseDTO;
import me.leeminsoo.usedpark.dto.item.ItemListResponseDTO;
import me.leeminsoo.usedpark.service.board.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.MediaSize;
import java.util.List;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final PostService postService;

    @GetMapping("")
    public String adminView(Model model) {
        AdminViewResponseDTO dto = adminService.getAdminViewData();
        model.addAttribute("data",dto);
        return "admin/admin";
    }

    @PostMapping("/board")
    public ResponseEntity<Void> addBoard(@RequestBody AddBoardRequestDTO dto){
        adminService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @GetMapping("/posts")
    public String getPosts(@RequestParam(name = "boardId",required = false) Long boardId,
                           @RequestParam(name = "order",defaultValue = "desc") String order,
                           @RequestParam(name = "page",defaultValue = "0" )int page,
                           @RequestParam(name = "size",defaultValue = "20") int size,
                           @RequestParam(name = "keyword",required = false) String keyword,
                           @RequestParam(name = "type",required = false) String type,
                           Model model) {
        Page<PostListViewResponseDTO> posts;
        if (keyword != null){
            posts = adminService.searchPosts(order,page,size,keyword,type);
        }else {
            if (boardId == null) {
                posts = adminService.findAllPosts(order, page, size);
            } else {
                posts = postService.getPosts(boardId, order, page, size);
            }
        }
        List<Board> boards = adminService.findBoard();
        model.addAttribute("board",boards);
        model.addAttribute("posts",posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("totalItems", posts.getTotalElements());
        model.addAttribute("order", order);
        model.addAttribute("size", size);
        return "admin/adminPost";
    }
    @GetMapping("/users")
    public String getUsers(@RequestParam(name = "order",defaultValue = "desc") String order,
                           @RequestParam(name = "page",defaultValue = "0" )int page,
                           @RequestParam(name = "size",defaultValue = "20") int size,
                           @RequestParam(name = "keyword",required = false) String keyword,
                           @RequestParam(name = "type",required = false)String type,
                           Model model) {
        Page<UserListViewResponseDTO> users;
        if(keyword == null){
            users = adminService.findAllUsers(order, page, size);
        }else {
            users = adminService.searchUsers(order,page,size,keyword,type);
        }

        model.addAttribute("users",users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("totalItems", users.getTotalElements());
        model.addAttribute("order", order);
        model.addAttribute("size", size);
        return "admin/adminUser";
    }
    @GetMapping("/items")
    public String getItems(@RequestParam(name = "order",defaultValue = "desc") String order,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "10") int size,
                           @RequestParam(name = "keyword",required = false) String keyword,
                           @RequestParam(name = "type",required = false) String type,
                           Model model) {
        Page<ItemListResponseDTO> items;
        if (keyword == null){
            items = adminService.findAllItems(order,page,size);
        }else {
            items = adminService.searchItems(order,page,size,keyword,type);
        }
        model.addAttribute("items",items);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", items.getTotalPages());
        model.addAttribute("totalItems", items.getTotalElements());
        model.addAttribute("order", order);
        model.addAttribute("size", size);
        return "admin/adminItem";
    }


    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable(name = "postId") Long postId, @AuthenticationPrincipal User user){
        adminService.deletePost(postId,user);
        return ResponseEntity.ok().build();

    }
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "userId") Long userId, @AuthenticationPrincipal User user){
        adminService.deleteUser(userId,user);
        return ResponseEntity.ok().build();

    }
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable(name = "itemId") Long itemId, @AuthenticationPrincipal User user){
        adminService.deleteItem(itemId,user);
        return ResponseEntity.ok().build();
    }

}
