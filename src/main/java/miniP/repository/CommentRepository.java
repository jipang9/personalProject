package miniP.repository;

import miniP.dto.comment.CommentResponseDto;
import miniP.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    @Query("select c from Comment c where c.board.id=:id")
    List<Comment> findCommentByBoardId(@Param("id")Long id);
}
