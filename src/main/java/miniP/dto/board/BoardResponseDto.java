package miniP.dto.board;

import lombok.*;
import miniP.entity.Board;
import miniP.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
public class BoardResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String username;
    private final List<Comment> comment;
    private final LocalDateTime createTime;
    private final LocalDateTime modDateTime;

    public BoardResponseDto(Board board) {
        this.id= board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getMember().getUsername();
        this.createTime = board.getCreateDate();
        this.modDateTime = board.getModDate();
        this.comment= board.getComments();
    }
    public static BoardResponseDto of(Board board){
        return new BoardResponseDto(board);
    }



}
