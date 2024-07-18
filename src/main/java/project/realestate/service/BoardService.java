package project.realestate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.realestate.dto.BoardDTO;

import java.util.List;

public interface BoardService {

    BoardDTO findByNo(Long bno);

    List<BoardDTO> findAll();

    Page<BoardDTO> findAll(Pageable pageable);

    Long register(BoardDTO boardDTO);

    void update(BoardDTO boardDTO);

    void delete(Long bno);
}
