package me.leeminsoo.usedpark.controller.mvc.post;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.dto.board.post.view.PostListViewResponseDTO;
import me.leeminsoo.usedpark.dto.board.post.view.PostResponseDTO;
import me.leeminsoo.usedpark.service.board.BoardService;
import me.leeminsoo.usedpark.service.board.PostService;
import me.leeminsoo.usedpark.util.CookieUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RequiredArgsConstructor
@Controller
public class PostViewController {

    private final PostService postService;
    private final BoardService boardService;



    @GetMapping("/board/{boardId}")
    public String getPosts(@PathVariable(name = "boardId") Long boardId, Model model,
                                @RequestParam(name = "order", defaultValue = "desc") String order,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "10") int size,
                                @RequestParam(name="keyword",required = false) String keyword,
                                @RequestParam(name = "type",required = false) String type) {
        Page<PostListViewResponseDTO> posts;
        if(order.equals("like")) {
            posts = postService.findByBoardIdOrderByLikes(boardId,page,size);
        }else {

            if (keyword != null){
                posts = postService.searchPost(boardId,keyword,type,order,page,size);

            }else {
                posts = postService.getPosts(boardId, order, page, size);
            }
        }

        String boardName;
        if(!posts.isEmpty()) {
            boardName = posts.getContent().get(0).getBoardName();
        }else{
            boardName = boardService.findByBoardName(boardId);
        }
        model.addAttribute("posts",posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("totalItems", posts.getTotalElements());
        model.addAttribute("order", order);
        model.addAttribute("size", size);
        model.addAttribute("boardName", boardName);
        model.addAttribute("boardId", boardId);
        return "post/postList";
    }


    @GetMapping("/new-post")
    public String newPost(@RequestParam(required = false,name = "postId") Long postId,
                          @RequestParam(name = "boardId") Long boardId,
                          Model model) {
        if (postId == null) {
            model.addAttribute("post" , new PostResponseDTO());
        } else {
            Post post = postService.findPost(postId);
            model.addAttribute("post",post);
        }
        model.addAttribute("boardId",boardId);
        return "post/new-post";
    }

    @GetMapping("/board/{boardId}/post/{postId}")
    public String getPost(@PathVariable(name = "boardId") Long boardId,
                          @PathVariable(name = "postId") Long postId,
                          HttpServletRequest request, HttpServletResponse response,
                          Model model) {
        boolean isView = CookieUtil.checkIfViewed(request,postId);
        if (!isView) {
            postService.updateView(postId);
            CookieUtil.setViewCookie(request,response,postId);
        }
        PostResponseDTO dto = postService.getPost(postId);


        model.addAttribute("post", dto);
        model.addAttribute("boardId", boardId);
        return "post/post";
    }


}
