package miniP.board.controller;


import lombok.RequiredArgsConstructor;
import miniP.board.dto.BoardRequestDto;
import miniP.board.dto.BoardResponseDto;
import miniP.board.dto.BoardsResponseDto;
import miniP.board.service.BoardService;
import miniP.security.member.MemberDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/posts") // 게시글 저장 -> return 데이터는 필요 없다고 생각한다 -> 그냥 작성하면 한거지? 안그래?
    public ResponseEntity<Void> save(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal MemberDetails memberDetails) {
        boardService.boardSave(boardRequestDto, memberDetails.getUsername());
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}") // 단건조회니까 -> 해당 데이터를 들고 와줘야 한다잉
    public ResponseEntity<BoardResponseDto> getOne(@PathVariable("id") Long id) {
        BoardResponseDto resultData = boardService.getOne(id);
        return ResponseEntity.ok().body(resultData);
    }

    @DeleteMapping("/delete/{id}") // in query 방식
    public HttpStatus deleteOne(@PathVariable("id") Long id, @AuthenticationPrincipal MemberDetails memberDetails) {
        boardService.deleteOne(id, memberDetails.getMember());
        return HttpStatus.OK;
    }

    @DeleteMapping("/deleteV2/{id}") //  entity를 그냥 넘겨버린다
    public HttpStatus deleteOneV2(@PathVariable("id") Long id, @AuthenticationPrincipal MemberDetails memberDetails) {
        boardService.deleteOneV2(id, memberDetails.getMember());
        return HttpStatus.OK;
    }

    @PatchMapping("/{id}") // 수정
    public ResponseEntity<Void> updateBoard(@PathVariable("id") Long id, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal MemberDetails memberDetails) {
        boardService.updateBoard(id, boardRequestDto, memberDetails.getMember());
        return ResponseEntity.status(201).build();
    }

    @GetMapping("") // 게시판 페이지라고 생각해보자
    // -> 모든 게시물의 제목과  작성자 명, comment 갯수, 좋아요 갯수, 조회수, 작성일자. 내용 정도만 뱉어내면 될 것 같다.
    public ResponseEntity<List<BoardsResponseDto>> getBoardList() {
        List<BoardsResponseDto> result = boardService.ListAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/list") // 페이징 처리
    public ResponseEntity<List<BoardsResponseDto>> getBoards
            (@RequestParam(defaultValue = "1") int page,
             @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<BoardsResponseDto> result = boardService.ListPaging(pageable, page - 1);
        return ResponseEntity.status(200).body(result);
    }

//    @GetMapping("/list/myboards") -> 이 기능은 MyService 이런식으로 뺴버리자
//    public ResponseEntity<List<BoardResponseDto>> getMyBoard(@AuthenticationPrincipal MemberDetails memberDetails) {
//        List<BoardResponseDto> result = boardService.myBoardList(memberDetails.getMember());
//        return ResponseEntity.status(200).body(result);
//    }

}
