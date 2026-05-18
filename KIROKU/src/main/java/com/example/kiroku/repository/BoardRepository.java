package com.example.kiroku.repository;

import com.example.kiroku.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
//BoardRepository는 인터페이스로 작성되며,
//JpaRepository를 상속받아 게시글 데이터를 DB에서 조회, 저장, 수정, 삭제할 수 있도록 하는 데이터 접근 계층
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Modifying
    @Query("update BoardEntity b set b.boardHits = b.boardHits + 1 where b.id = :id")
    void updateHits(@Param("id") Long id);

    List<BoardEntity> findAllByUser_IdOrderByIdDesc(Long userId);
}
