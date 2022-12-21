package miniP.dto.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import miniP.dto.board.BoardResponseDto;
import miniP.entity.Board;
import miniP.entity.Comment;

@RequiredArgsConstructor
@Getter
public class CommentResponseDto {

    private final String comment;
    private final String username;

    public CommentResponseDto(Comment comment) {
        this.comment = comment.getComment();
        this.username = comment.getMember().getUsername();
    }
    public static CommentResponseDto of(Comment comment){
        return new CommentResponseDto(comment);
    }
}
