package miniP.dto.board;
import lombok.*;
import miniP.entity.Board;
import miniP.entity.Member;

@Getter
@RequiredArgsConstructor
public class BoardRequestDto {

    private final String title;
    private final String content;

    // 이게 정답일까여?


    public static Board toEntity(BoardRequestDto boardRequestDto, Member member){
        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .member(member)
                .build();
        return board;
    }

}
