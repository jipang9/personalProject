package miniP.dto.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import miniP.dto.comment.CommentResponseDto;
import miniP.entity.Board;

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

    // 과연 시점으로부터 어떻게 데이터를 반환할 것인지

    // 필요한  dto는 그냥 만들어서 사용할 것. (다른 튜터님께도 질문할 것) 중요---****

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

    // save 시점으로 하나 ->
    public static BoardResponseDto of(Board board) {
        return new BoardResponseDto(board);
    }

}
