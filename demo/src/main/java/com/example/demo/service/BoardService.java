package com.example.demo.service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.BoardEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public List<BoardDTO> findAllByUser(Long userId) {
        List<BoardEntity> boardEntityList =
                boardRepository.findAllByUser_IdOrderByIdDesc(userId);

        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    public void save(BoardDTO boardDTO, Long loginUserId) {
        UserEntity user = userService.findById(loginUserId);
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO, user);
        boardRepository.save(boardEntity);
    }

    public BoardDTO findById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return BoardDTO.toBoardDTO(boardEntity);
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public boolean isOwner(Long boardId, Long loginUserId) {
        BoardEntity boardEntity = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return boardEntity.getUser().getId().equals(loginUserId);
    }

    @Transactional
    public void update(BoardDTO boardDTO) {
        BoardEntity boardEntity = boardRepository.findById(boardDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
        boardEntity.update(boardDTO);
    }
}
