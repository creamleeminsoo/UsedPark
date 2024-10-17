package me.leeminsoo.usedpark.service.board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.notpound.CommentNotFoundException;
import me.leeminsoo.usedpark.domain.board.Comment;
import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.alarm.CommentAlarmDTO;
import me.leeminsoo.usedpark.dto.board.comment.AddCommentRequestDTO;
import me.leeminsoo.usedpark.dto.board.comment.UpdateCommentDTO;
import me.leeminsoo.usedpark.repository.board.CommentRepository;
import me.leeminsoo.usedpark.service.alarm.AlarmService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final AlarmService alarmService;

    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    }

    public Comment saveComment(AddCommentRequestDTO dto,Long postId) {
        Post post = postService.findPost(postId);
        Comment comment = Comment.builder().content(dto.getContent()).post(post).user(dto.getUser()).build();
        CommentAlarmDTO alarmDTO = CommentAlarmDTO.builder().postId(postId).createdAt(LocalDateTime.now()).boardId(post.getBoard().getId()).build();
        alarmService.customAlarm(post.getUser().getId(),alarmDTO,"작성하신 글에 댓글이 달렸습니다.","comment");
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId,User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        if (comment.getUser().getId().equals(user.getId())) {
            commentRepository.deleteById(commentId);
        }else throw new AccessDeniedException("권한이 없습니다");
    }

    @Transactional
    public Comment updateComment(Long commentId, UpdateCommentDTO dto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        if (comment.getUser().getId().equals(user.getId())) {
            comment.update(dto.getContent());
        }else throw new AccessDeniedException("권한이 없습니다");
        return comment;
    }
}
