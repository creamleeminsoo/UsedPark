package me.leeminsoo.usedpark.service.board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.InvalidInput.LikeDuplicationException;
import me.leeminsoo.usedpark.config.error.exception.UserNotAuthenticationException;
import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.alarm.LikeAlarmDTO;
import me.leeminsoo.usedpark.repository.board.LikeRepository;
import me.leeminsoo.usedpark.service.alarm.AlarmService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostService postService;
    private final AlarmService alarmService;


    @Transactional
    public int addLike(Long postId , User user) {
        if (user == null){
            throw new UserNotAuthenticationException();
        }
        Post post = postService.findPost(postId);

        if (!likeRepository.existsByPostAndUser(post,user)) {
            likeRepository.addLike(postId,user.getId());

            LikeAlarmDTO likeAlarmDTO = LikeAlarmDTO.builder().postId(postId).createdAt(LocalDateTime.now()).boardId(post.getBoard().getId()).build();
            alarmService.customAlarm(post.getUser().getId(),likeAlarmDTO,"작성하신 글에 좋아요가 달렸습니다","like");
            return likeRepository.countLikesByPostId(postId);

        }else throw new LikeDuplicationException();
    }


}
