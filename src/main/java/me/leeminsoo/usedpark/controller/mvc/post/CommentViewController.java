package me.leeminsoo.usedpark.controller.mvc.post;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.domain.board.Comment;
import me.leeminsoo.usedpark.service.board.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class CommentViewController {
    private final CommentService commentService;

    @GetMapping("/update-comment/{commentId}")
    public String updateComment(@PathVariable(name = "commentId")Long commentId, Model model) {
        Comment comment = commentService.findByComment(commentId);
        model.addAttribute("comment",comment);
        return "post/update-comment";

    }
}
