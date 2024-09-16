package me.leeminsoo.usedpark.service.board;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.notpound.BoardNotFoundException;
import me.leeminsoo.usedpark.domain.board.Board;
import me.leeminsoo.usedpark.repository.board.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public Board getBoard(Long board) {
        return boardRepository.findById(board).orElseThrow(BoardNotFoundException::new);
    }
    public List<Board> getBoards(){
        return boardRepository.findAll();
    }

}
