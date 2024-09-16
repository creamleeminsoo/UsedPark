package me.leeminsoo.usedpark.repository.board;

import me.leeminsoo.usedpark.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BoardRepository extends JpaRepository<Board, Long> {
    long count();



}
