package miniP.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import miniP.board.entity.Board;
import miniP.comment.dto.CommentResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class BoardResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String username;
    private final List<CommentResponseDto> commentList;
    private final LocalDateTime createTime;
    private final LocalDateTime modDateTime;


    public BoardResponseDto(Board board, List<CommentResponseDto> comments) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getMember().getUsername();
        this.createTime = board.getCreateDate();
        this.modDateTime = board.getModDate();
        this.commentList =comments;
    }

    public static BoardResponseDto of(Board board, List<CommentResponseDto> comments) {
        return new BoardResponseDto(board, comments);
    }

}
