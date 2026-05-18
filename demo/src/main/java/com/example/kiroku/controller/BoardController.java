package com.example.kiroku.controller;

import com.example.kiroku.dto.BoardDTO;
import com.example.kiroku.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
//BoardController는 사용자의 요청(조회, 생성, 수정, 삭제)을 받아 필요한 데이터를 Service 계층에 전달하여
// 비즈니스 로직을 처리하고, 그 결과를 Model에 담아 View에 전달하여 화면을 출력하는 역할
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    private Long requireLogin(HttpSession session) {
        return (Long) session.getAttribute("loginUserId");
    }//session에서 loginUserId 값을 꺼내서 Long 타입으로 반환하는 메소드

    @GetMapping("/save")
    public String saveForm(HttpSession session) {
        Long loginUserId = requireLogin(session);
        if (loginUserId == null) return "redirect:/login";
        return "save";
    }//세션에 loginUserId가 있으면 save 페이지, 없으면 로그인 페이지로 리다이렉트

    @PostMapping("/save")
    public String save(BoardDTO boardDTO, HttpSession session) {
        Long loginUserId = requireLogin(session);
        if (loginUserId == null) return "redirect:/login";

        boardService.save(boardDTO, loginUserId);
        //boardDTO와 loginUserId를 전달하면 BoardService의 save 메소드를 실행해라
        return "redirect:/board/";
    }

    @GetMapping("/")
    public String findAll(Model model, HttpSession session) {
        Long loginUserId = requireLogin(session);
        if (loginUserId == null) return "redirect:/login";

        List<BoardDTO> boardDTOList = boardService.findAllByUser(loginUserId);
        //loginUserId를 boardService의 findAllByUser 메소드에 전달해서
        //해당 사용자의 게시글 목록(List<BoardDTO>)을 받아서
        //boardDTOList 변수에 저장하는 것
        model.addAttribute("boardList", boardDTOList);
        //boardDTOList 데이터를
        //"boardList"라는 이름으로 Model에 저장해서
        //View(html)에서 사용할 수 있게 하는 것
        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id,
                           Model model,
                           HttpSession session,
                           RedirectAttributes ra) {
        //URL 경로에서 id 값을 받아오고,
        //View로 데이터를 전달하기 위한 Model과
        //로그인 정보를 확인하기 위한 Session,
        //redirect 시 메시지를 전달하기 위한 RedirectAttributes를 매개변수로 받는 메소드

        Long loginUserId = requireLogin(session);
        if (loginUserId == null) return "redirect:/login";

        if (!boardService.isOwner(id, loginUserId)) {
            ra.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
            //작성자가 아니면 메세지 전달
            return "redirect:/board/";
        }

        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "detail";
        //updateHits(id) → 해당 게시글 조회수 증가
        //findById(id) → 해당 게시글 정보 조회
        //결과를 boardDTO 변수에 저장
        //"board"라는 이름으로 Model에 추가
        //detail.html 화면 반환
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         HttpSession session,
                         RedirectAttributes ra) {

        Long loginUserId = requireLogin(session);
        if (loginUserId == null) return "redirect:/login";

        if (!boardService.isOwner(id, loginUserId)) {
            ra.addFlashAttribute("errorMessage", "삭제 권한이 없습니다.");
            return "redirect:/board/";
        }

        boardService.delete(id);
        return "redirect:/board/";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id,
                             Model model,
                             HttpSession session,
                             RedirectAttributes ra) {

        Long loginUserId = requireLogin(session);
        if (loginUserId == null) return "redirect:/login";

        if (!boardService.isOwner(id, loginUserId)) {
            ra.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
            return "redirect:/board/";
        }

        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(BoardDTO boardDTO,
                         HttpSession session,
                         RedirectAttributes ra) {

        Long loginUserId = requireLogin(session);
        if (loginUserId == null) return "redirect:/login";

        if (!boardService.isOwner(boardDTO.getId(), loginUserId)) {
            ra.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
            return "redirect:/board/";
        }

        boardService.update(boardDTO);
        return "redirect:/board/" + boardDTO.getId();
    }
}
