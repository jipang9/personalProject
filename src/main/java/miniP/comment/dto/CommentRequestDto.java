package miniP.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import miniP.board.entity.Board;
import miniP.comment.entity.Comment;
import miniP.member.entity.Member;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class CommentRequestDto {

    private final String comment;

    public Comment toEntity(Board board, Member member){
        return Comment.builder()
                .comment(this.getComment())
                .member(member)
                .board(board)
                .recommendCount(0L)
                .build();
    }


}
