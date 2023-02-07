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
    @Query("delete  from Comment  c where c.board=:board")
    void deleteAllByBoard(@Param("board") Board board); // comment가 board를

    @Modifying
    @Transactional
    @Query("delete from Comment c where c.id in :id")
    void deleteAllByBoardIds(@Param("id") List<Long> id); // update 벌크연산시 아마 clearAuthmatically 속성을 true로 바꿔줘야 할 듯.
    // nativequery ( message보기..) // batchsize 늘리기. // 처리 단위를 정하자


    @Query("select c.id from Comment c where c.board.id=:boardId")
    List<Long> findAllByBoardId(@Param("boardId") Long boardId); // 커맨트 id 들고오기 위함



}
