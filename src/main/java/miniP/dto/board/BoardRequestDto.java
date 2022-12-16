package miniP.dto.board;
import lombok.*;
import miniP.entity.Board;
import miniP.entity.Member;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {

    private String title;
    private String content;


    public Board toEntity(BoardRequestDto boardRequestDto, Member member){
        Board board = Board.builder()
                .content(boardRequestDto.getContent())
                .title(boardRequestDto.getTitle())
                .member(member)
                .build();
        return board;
    }

}
