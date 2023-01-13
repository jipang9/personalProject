package miniP.comment.repository;

import miniP.board.entity.Board;
import miniP.comment.dto.CommentResponseDto;
import miniP.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    @Query("select c from Comment c where c.board.id=:id")
    List<CommentResponseDto> findCommentByBoardId(@Param("id")Long id);

    @Query("select count(c.member.id) from Comment c where c.board.id=:id ")
    Long countCommentByBoardId(@Param("id")Long id);



    @Modifying
    @Transactional
    @Query("delete from Comment c where c.id in :id")
    void deleteAllByBoardIds(@Param("id") List<Long> id); // 오늘의 맛집


    @Query("select c.id from Comment c where c.board.id=:boardId")
    List<Long> findAllByBoardId(@Param("boardId") Long boardId); // 커맨트 id 들고오기 위함

}
