package miniP.board.dto;

import lombok.Getter;
import miniP.board.entity.Board;

import java.time.LocalDateTime;

@Getter
public class BoardsResponseDto {

    // -> 모든 게시물의 제목과  작성자 명, comment 갯수, 좋아요 갯수, 조회수, 작성일자. 내용 정도만 뱉어내면 될 것 같다.
    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final Long commentCount;
    private final LocalDateTime localDateTime;


    public BoardsResponseDto(Board board, Long commentCount) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getMember().getUsername();
        this.commentCount = commentCount;
        this.localDateTime = board.getCreateDate();
    }


}
