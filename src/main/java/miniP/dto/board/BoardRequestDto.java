package miniP.dto.board;
import lombok.*;
import miniP.entity.Board;
import miniP.entity.Member;

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
