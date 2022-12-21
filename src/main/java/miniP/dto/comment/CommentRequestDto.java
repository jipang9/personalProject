package miniP.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import miniP.entity.Board;
import miniP.entity.Comment;
import miniP.entity.Member;

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
                .build();
    }


}
