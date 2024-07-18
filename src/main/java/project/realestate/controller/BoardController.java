package project.realestate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.realestate.dto.BoardDTO;
import project.realestate.service.BoardService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    // 게시판 메인 페이지 + 페이지네이션
    @GetMapping("/main")
    public String main(Model model, @RequestParam(defaultValue = "0", required = false) Integer page){
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "bno"));
        Page<BoardDTO> boardPage = boardService.findAll(pageable);
        //List<BoardDTO> boardDTOList = boardService.findAll();
        //log.info("boardDTOList.size : " + boardDTOList.size());
        //Collections.reverse(boardDTOList); // 최신등록순 나열
        //model.addAttribute("boardDTOList", boardDTOList);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("currentPage", page);
        return "/board/main";
    }

    // 게시글 등록 페이지
    @GetMapping("/register")
    public String register(){
        return "/board/register";
    }

    // 게시글 상세 페이지
    @GetMapping("/detail/{bno}")
    public String detail(Model model, @PathVariable("bno") Long bno){
        BoardDTO boardDTO = boardService.findByNo(bno);
        model.addAttribute("boardDTO", boardDTO);
        return "/board/detail";
    }

    // 게시글 수정 페이지 // @RequestParam -> @PathVariable, + {bno}
    @GetMapping("/update/{bno}")
    public String update(@PathVariable("bno") Long bno, Model model){
        BoardDTO boardDTO = boardService.findByNo(bno);
        model.addAttribute("boardDTO", boardDTO);
        return "/board/update";
    }

    // 게시글 등록 처리
    @PostMapping("/register")
    public String register(@ModelAttribute BoardDTO boardDTO, @RequestParam("imageFiles") MultipartFile[] imageFiles) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        String uploadDir = "C:/uploads/images/";

        for (MultipartFile imageFile : imageFiles) {
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                File destinationFile = new File(uploadDir + fileName);
                imageFile.transferTo(destinationFile); // 파일 저장
                imagePaths.add(fileName); // 경로 설정

                // 파일을 src/main/resources/static/images/ 디렉토리로 복사
                Path sourcePath = destinationFile.toPath();
                Path destinationPath = Paths.get("src/main/resources/static/images/" + fileName);
                try {
                    Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING); // 파일이 이미 존재할경우, 덮어쓰기
                } catch (IOException e) {
                    log.error("파일 복사 중 오류 발생: " + e.getMessage());
                }
            }
        }

        boardDTO.setImagePaths(imagePaths);

        log.info("boardDTO : " + boardDTO);
        boardService.register(boardDTO);
        return "redirect:/board/main";
    }

    // 게시글 수정 처리
    @PostMapping("/update/{bno}")
    public String update(@ModelAttribute BoardDTO boardDTO){
        log.info("boardDTO : " + boardDTO);
        boardService.update(boardDTO);
        return "redirect:/board/detail/" + boardDTO.getBno();
    }

    // 게시글 삭제 처리
    @PostMapping("/delete/{bno}")
    public String delete(@PathVariable("bno") Long bno){
        log.info("delete board with bno : " + bno);
        boardService.delete(bno);
        return "redirect:/board/main";
    }
}
