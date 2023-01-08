package miniP.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import miniP.comment.dto.CommentResponseDto;
import miniP.board.entity.Board;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getMember().getUsername();
        this.createTime = board.getCreateDate();
        this.modDateTime = board.getModDate();
        if(board.getComments()==null)
            this.comment=new ArrayList<>();
        else
        this.comment = board.getComments().stream().map(CommentResponseDto::of).collect(Collectors.toList());
    }


    public static BoardResponseDto of(Board board) {
        return new BoardResponseDto(board);
    }

}
