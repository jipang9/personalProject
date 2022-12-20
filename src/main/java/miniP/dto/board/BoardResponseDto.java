package miniP.dto.board;

import lombok.*;
import miniP.entity.Board;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class BoardResponseDto {

    private final String title;
    private final String content;
    private final String username;
    private final LocalDateTime createTime;
    private final LocalDateTime modDateTime;

    public  static BoardResponseDto toDto(Board board){
        BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .username(board.getMember().getUsername())
                .createTime(board.getCreateDate())
                .modDateTime(board.getModDate())
                .build();
        return boardResponseDto;
    }

}
