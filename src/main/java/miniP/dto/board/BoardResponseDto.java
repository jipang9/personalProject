package miniP.dto.board;

import lombok.*;
import miniP.dto.comment.CommentResponseDto;
import miniP.entity.Board;
import miniP.entity.Comment;
import miniP.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class BoardResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String username;
    private final List<CommentResponseDto> comment;
    private final LocalDateTime createTime;
    private final LocalDateTime modDateTime;

    public BoardResponseDto(Board board) {

        this.id= board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getMember().getUsername();
        this.createTime = board.getCreateDate();
        this.modDateTime = board.getModDate();
        this.comment = board.getComments().stream().map(CommentResponseDto::of).collect(Collectors.toList());
    }
    public static BoardResponseDto of(Board board){
        return new BoardResponseDto(board);
    }


}
