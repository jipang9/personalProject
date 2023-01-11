package miniP.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import miniP.comment.entity.Comment;

@RequiredArgsConstructor
@Getter
public class CommentResponseDto {

    private final String comment;
    private final String writer;

    public CommentResponseDto(Comment comment) {
        this.comment = comment.getComment();
        this.writer = comment.getMember().getUsername();
    }
    public static CommentResponseDto of(Comment comment){
        return new CommentResponseDto(comment);
    }

}
