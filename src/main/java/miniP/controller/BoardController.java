package miniP.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.dto.BoardRequestDto;
import miniP.dto.BoardResponseDto;
import miniP.exception.PostException;
import miniP.service.BoardService;
import miniP.service.ResponseService;
import miniP.service.result.SingleResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    // save 로직에 대한 고민
    // 1) 사용자는 정보를 입력하고, 해당 게시글 작성이 완료되었을 때, 데이터를 리턴받게 된다면  어떤 데이터가 필요할까?
    // 비밀번호는 노출할 필요가 없지 않을까?? -> 그래서 비밀번호를 노출하지 않음.
    // 과연 프론트 입장에서는 어떤 데이터를 필요로 할 까? -> 데이터를 다 반환하고 알아서 프론트에서?해야하나?

//    @PostMapping("/save") // 피드백 내용 -> 데이터만 줘도 되는데 굳이 쓸곳 없는 정보를 준다
//    public SingleResult<BoardRequestDto> save(@RequestBody BoardRequestDto boardRequestDto) {
//        boardService.save(boardRequestDto);
//        return responseService.getSingleResult(boardRequestDto);
//    }

    // 지금은 데이터가
    @PostMapping("/save")
    public ResponseEntity<BoardResponseDto> save(@RequestBody BoardRequestDto boardRequestDto) throws Exception {
        return ResponseEntity.ok().body(boardService.save(boardRequestDto));
    }

    @GetMapping("/get/{id}")
    public SingleResult<BoardResponseDto> getOne(@PathVariable("id") Long id) {
        BoardResponseDto result = boardService.getOne(id);
        return responseService.getSingleResult(result);
    }

    // 피드백 적용 전
//    @DeleteMapping("/delete/{id}")
//    public SingleResult<String> deleteOne(@PathVariable("id") Long id, @RequestParam String password) {
//        boardService.deleteOne(id, password);
//        return responseService.getSingleResult("삭제완료");
//    }

    @DeleteMapping("/delete/{id}") // 피드백 적용 후
    public HttpStatus deleteOne(@PathVariable("id") Long id, @RequestParam String password) {
        boardService.deleteOne(id, password);
        return HttpStatus.OK;
    }

    // 데이터의 분리는 어떻게 이루어져야하는지?

//    @PutMapping("/modify/{id}")
//    public ResponseEntity<BoardRequestDto> modifyBoard(@PathVariable("id")Long id, @RequestParam String password){
//        BoardRequestDto result =boardService.modify(id,password, boardRequestDto);
//        return ResponseEntity.ok().body(result);
//    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable("id")Long id, @RequestBody BoardRequestDto boardRequestDto){
        return ResponseEntity.ok().body(boardService.updateBoard(id,boardRequestDto));
    }


    @GetMapping("/list")
    public ResponseEntity<List<BoardResponseDto>> getBoardList(){
        List<BoardResponseDto> getBoardList = boardService.findAll();
        return ResponseEntity.ok().body(getBoardList);
    }
}
