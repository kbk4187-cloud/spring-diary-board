package com.example.kiroku.dto;

import com.example.kiroku.entity.BoardEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
//BoardDTO는 게시글 데이터를 View로 전달하기 위한 객체이며,
// BoardEntity의 데이터를 DTO 형태로 변환하는 정적 메소드를 통해 Entity와 View 사이의 데이터 전달 역할
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private String boardCreatedAt;

    private String dateFormat(LocalDateTime date) {
        if (date == null) return null;
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedAt(boardDTO.dateFormat(boardEntity.getCreatedAt()));
        return boardDTO;
    }
}
