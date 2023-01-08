package miniP.board.dto;
import lombok.*;
import miniP.board.entity.Board;
import miniP.member.entity.Member;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class BoardRequestDto {

    private final String title;
    private final String content;

    public Board toEntity(Member member){
        Board board = Board.builder()
                .title(this.getTitle())
                .content(this.getContent())
                .member(member)
                .build();
        return board;
    }

}
