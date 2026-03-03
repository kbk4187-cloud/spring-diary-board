package com.example.demo.entity;

import com.example.demo.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board_table_2025")
public class BoardEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ 화면에서 입력받지 않고, 로그인 유저 username으로 자동 저장
    @Column(nullable = false)
    private String boardWriter;

    @Column(nullable = false)
    private String boardTitle;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String boardContents;

    @Column
    private int boardHits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // ✅ 저장용
    public static BoardEntity toSaveEntity(BoardDTO boardDTO, UserEntity user) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.user = user;
        boardEntity.boardWriter = user.getUsername();
        boardEntity.boardTitle = boardDTO.getBoardTitle();
        boardEntity.boardContents = boardDTO.getBoardContents();
        boardEntity.boardHits = 0;
        return boardEntity;
    }

    public void update(BoardDTO boardDTO) {
        this.boardTitle = boardDTO.getBoardTitle();
        this.boardContents = boardDTO.getBoardContents();
    }
}
