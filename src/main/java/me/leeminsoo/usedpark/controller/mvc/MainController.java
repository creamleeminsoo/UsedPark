package me.leeminsoo.usedpark.controller.mvc;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.domain.board.Board;
import me.leeminsoo.usedpark.dto.board.post.view.PopularPostsResponseDTO;
import me.leeminsoo.usedpark.dto.item.ItemListResponseDTO;
import me.leeminsoo.usedpark.service.board.BoardService;
import me.leeminsoo.usedpark.service.board.PostService;
import me.leeminsoo.usedpark.service.item.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final PostService postService;
    private final ItemService itemService;
    private final BoardService boardService;

    @GetMapping("/")
    public String mainPage(Model model) {

        List<Board> boards = boardService.getBoards();
        List<ItemListResponseDTO> items = itemService.getRecentItems();
        List<PopularPostsResponseDTO> posts = postService.getPopularPosts();

        model.addAttribute("boards",boards);
        model.addAttribute("items",items);
        model.addAttribute("posts",posts);

        return "index";
    }
}
