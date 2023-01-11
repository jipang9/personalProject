package miniP.comment.repository;

import miniP.comment.dto.CommentResponseDto;
import miniP.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    @Query("select c from Comment c where c.board.id=:id")
    List<CommentResponseDto> findCommentByBoardId(@Param("id")Long id);

    @Query("select count(c.member.id) from Comment c where c.board.id=:id ")
    Long countCommentByBoardId(@Param("id")Long id);
}
