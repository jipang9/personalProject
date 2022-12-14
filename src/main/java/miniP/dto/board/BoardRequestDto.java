package miniP.dto.board;


import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {

    private String title;
    private String content;
    private String name;
    private String password;


    @Builder
    public BoardRequestDto(String title, String content, String name, String password) {
        this.title = title;
        this.content = content;
        this.name = name;
        this.password = password;
    }


}
