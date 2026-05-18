package com.example.kiroku.entity;

import com.example.kiroku.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
//BoardEntity는 게시글 정보를 DB 테이블과 매핑하는 클래스이며, DTO의 데이터를 Entity로 변환하는 메소드를 가지고 있고,
// 작성자(UserEntity)와는 다대일(ManyToOne) 관계로 설정
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
