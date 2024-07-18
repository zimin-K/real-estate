package project.realestate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.realestate.dto.BoardDTO;
import project.realestate.entity.Board;
import project.realestate.repository.BoardRepository;
import project.realestate.service.BoardService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    @Override
    public BoardDTO findByNo(Long bno) {
        Optional<Board> optionalBoard = boardRepository.findById(bno);
        BoardDTO boardDTO = modelMapper.map(optionalBoard, BoardDTO.class);
        return boardDTO;
    }

    // stream() : 리스트의 요소들을 하나씩 처리할 수 있는 추상적인 구조
    // collect() : 스트림의 요소들을 수집하여 원하는 컬렉션으로 만듦
    // Collectors.toList() :  스트림의 결과를 List로 수집
    @Override
    public List<BoardDTO> findAll() { // Board -> BoardDTO.class 끝까지 반복
        List<Board> boardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "bno"));
        return boardList.stream().map(board ->
                modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());
        // 로그추가
        //dtoList.forEach(boardDTO -> log.info("BoardDTO: " + boardDTO));
        //return dtoList;
    }

    @Override
    public Page<BoardDTO> findAll(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "bno"));
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage.map(board ->
                modelMapper.map(board, BoardDTO.class));
    }

    @Override
    public Long register(BoardDTO boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class);
        Long bno = boardRepository.save(board).getBno();
        return bno;
    }

    @Override
    public void update(BoardDTO boardDTO) {
        Optional<Board> optionalBoard = boardRepository.findById(boardDTO.getBno());
        Board board = optionalBoard.orElseThrow();
        board.change(boardDTO.getTitle(), boardDTO.getAddress(), boardDTO.getSize(), boardDTO.getPrice(), boardDTO.getContent(), boardDTO.getImagePaths());
        boardRepository.save(board);
    }

    @Override
    public void delete(Long bno) {
        boardRepository.deleteById(bno);
    }
}
