package me.leeminsoo.usedpark.service.board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.leeminsoo.usedpark.config.error.exception.InvalidInput.LikeDuplicationException;
import me.leeminsoo.usedpark.config.error.exception.UserNotAuthenticationException;
import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.alarm.LikeAlarmDTO;
import me.leeminsoo.usedpark.repository.board.LikeRepository;
import me.leeminsoo.usedpark.service.alarm.AlarmService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostService postService;
    private final AlarmService alarmService;


//    @Transactional
//    public int addLike(Long postId, User user) {
//        if (user == null) {
//            throw new UserNotAuthenticationException();
//        }
//
//        long startTime = System.currentTimeMillis();
//
//        Post post = postService.findPost(postId);
//
//        if (!likeRepository.existsByPostAndUser(post, user)) {
//            likeRepository.addLike(postId, user.getId());
//
//            LikeAlarmDTO likeAlarmDTO = LikeAlarmDTO.builder()
//                    .postId(postId)
//                    .createdAt(LocalDateTime.now())
//                    .boardId(post.getBoard().getId())
//                    .build();
//
//            alarmService.customAlarm(post.getUser().getId(), likeAlarmDTO, "작성하신 글에 좋아요가 달렸습니다", "like");
//            int likeCount = likeRepository.countLikesByPostId(postId);
//
//            long endTime = System.currentTimeMillis();
//
//            long durationInMillis = endTime - startTime;
//
//            System.out.println("addLike 실행 시간(복합 인덱스 o, 동기 처리 o): " + durationInMillis + " ms");
//
//            return likeCount;
//        } else {
//            throw new LikeDuplicationException();
//        }
//    }

    @Transactional
    public int addLike(Long postId, User user) {
        if (user == null) {
            throw new UserNotAuthenticationException();
        }

        Post post = postService.findPost(postId);

        if (!likeRepository.existsByPostAndUser(post, user)) {
            likeRepository.addLike(postId, user.getId());

            sendLikeNotification(postId,post,user);

            return likeRepository.countLikesByPostId(postId);
        } else {
            throw new LikeDuplicationException();
        }
    }

    @Async("threadPoolTaskExecutor")
    public void sendLikeNotification(Long postId,Post post, User user){
        try{
            LikeAlarmDTO likeAlarmDTO = LikeAlarmDTO.builder()
                    .postId(postId)
                    .createdAt(LocalDateTime.now())
                    .boardId(post.getBoard().getId())
                    .build();
            alarmService.customAlarm(
                    post.getUser().getId(),
                    likeAlarmDTO,
                    "작성하신 글에 좋아요가 달렸습니다",
                    "like"
            );
        }catch (Exception e){
            log.error("Failed to send like notification for postId {} and userId {}", postId, user.getId(), e);
        }
    }


}
