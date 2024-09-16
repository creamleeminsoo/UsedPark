package me.leeminsoo.usedpark.repository.board;


import me.leeminsoo.usedpark.domain.board.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<PostImage,Long> {
    void deleteByPostId(Long postId);
    List<PostImage> findByPostId(Long postId);
}
