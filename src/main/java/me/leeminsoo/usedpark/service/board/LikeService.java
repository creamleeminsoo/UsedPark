package me.leeminsoo.usedpark.service.board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.InvalidInput.LikeDuplicationException;
import me.leeminsoo.usedpark.config.error.exception.UserNotAuthenticationException;
import me.leeminsoo.usedpark.config.error.exception.notpound.PostNotFoundException;
import me.leeminsoo.usedpark.domain.board.Post;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.repository.board.LikeRepository;
import me.leeminsoo.usedpark.repository.board.PostRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;


    @Transactional
    public int addLike(Long postId , User user) {
        if (user == null){
            throw new UserNotAuthenticationException();
        }
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        if (!likeRepository.existsByPostAndUser(post,user)) {
            likeRepository.addLike(postId,user.getId());

            return likeRepository.countLikesByPostId(postId);

        }else throw new LikeDuplicationException();
    }


}
