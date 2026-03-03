package com.example.demo.controller;

import com.example.demo.dto.BoardDTO;
import com.example.demo.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    private Long requireLogin(HttpSession session) {
        return (Long) session.getAttribute("loginUserId");
    }

    @GetMapping("/save")
    public String saveForm(HttpSession session) {
        Long loginUserId = requireLogin(session);
        if (loginUserId == null) return "redirect:/login";
        return "save";
    }

    @PostMapping("/save")
    public String save(BoardDTO boardDTO, HttpSession session) {
        Long loginUserId = requireLogin(session);
        if (loginUserId == null) return "redirect:/login";

        boardService.save(boardDTO, loginUserId);
        return "redirect:/board/";
    }

    @GetMapping("/")
    public String findAll(Model model, HttpSession session) {
        Long loginUserId = requireLogin(session);
        if (loginUserId == null) return "redirect:/login";

        List<BoardDTO> boardDTOList = boardService.findAllByUser(loginUserId);
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id,
                           Model model,
                           HttpSession session,
                           RedirectAttributes ra) {

        Long loginUserId = requireLogin(session);
        if (loginUserId == null) return "redirect:/login";

        if (!boardService.isOwner(id, loginUserId)) {
            ra.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
            return "redirect:/board/";
        }

        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "detail";
    }

    // ✅ 삭제는 POST로!
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
