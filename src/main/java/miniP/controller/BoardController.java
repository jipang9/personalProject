package miniP.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.dto.BoardRequestDto;
import miniP.dto.BoardResponseDto;
import miniP.exception.BoardDeleteException;
import miniP.exception.BoardUpdateException;
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
//    private final ResponseService responseService;

    // save 로직에 대한 고민
    // 1) 사용자는 정보를 입력하고, 해당 게시글 작성이 완료되었을 때, 데이터를 리턴받게 된다면  어떤 데이터가 필요할까?
    // 비밀번호는 노출할 필요가 없지 않을까?? -> 그래서 비밀번호를 노출하지 않음.
    // 과연 프론트 입장에서는 어떤 데이터를 필요로 할까? -> 데이터를 다 반환하고 알아서 프론트에서?해야하나?

//    @PostMapping("/save") // 피드백 내용 -> 데이터만 줘도 되는데 굳이 쓸곳 없는 정보를 준다
//    public SingleResult<BoardRequestDto> save(@RequestBody BoardRequestDto boardRequestDto) {
//        boardService.save(boardRequestDto);
//        return responseService.getSingleResult(boardRequestDto);
//    }

    // 지금은 데이터가 많이 안 들어가서 굳이 예외처리를 할 것이 많이 없을 것 같은데
    @PostMapping("/save")
    public ResponseEntity<BoardResponseDto> save(@RequestBody BoardRequestDto boardRequestDto) throws Exception {
        try {
            return ResponseEntity.ok().body(boardService.save(boardRequestDto));
        } catch (Exception e) {
            throw new PostException();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BoardResponseDto> getOne(@PathVariable("id") Long id) {
        BoardResponseDto result = boardService.getOne(id);
        return ResponseEntity.ok().body(result);
    }

    // 피드백 적용 전
//    @DeleteMapping("/delete/{id}")
//    public SingleResult<String> deleteOne(@PathVariable("id") Long id, @RequestParam String password) {
//        boardService.deleteOne(id, password);
//        return responseService.getSingleResult("삭제완료");
//    }


    //(12.08)HTTP method 선정이 중요합니다.
    @DeleteMapping("/delete/{id}") // 피드백 적용 후
    public HttpStatus deleteOne(@PathVariable("id") Long id, @RequestParam String password) throws RuntimeException {
        try {
            if (boardService.checkByPassword(id, password) == true) {
                boardService.deleteOne(id);
                return HttpStatus.OK;
            }else{
                throw new BoardDeleteException("비밀번호 불일치");
            }
        } catch (RuntimeException e) {
            throw new BoardDeleteException();
        }
    }

//    @PutMapping("/modify/{id}")
//    public ResponseEntity<BoardRequestDto> modifyBoard(@PathVariable("id")Long id, @RequestParam String password){
//        BoardRequestDto result =boardService.modify(id,password, boardRequestDto);
//        return ResponseEntity.ok().body(result);
//    }


// 궁금한점 -> 위에보면 파람으로 password 날리는데 여기는 body에 다 실어서 보냄, 어떤 방법이 맞는건지
    // 결국 사용자 입장에서는 해당 게시글을 지울 때, 먼저 pk값과 비밀번호를 넘기게 되는데 이때 pk값으로 정보를 들고있어야 하고, 비밀번호 검증 후
    // 수정 작업이 진행되어야 한다. @RequestBody와 RequestParam은 같이 쓸 수 없는건지?
    @PutMapping("/modify/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable("id") Long id, @RequestBody BoardRequestDto boardRequestDto) {
        try{
            if(boardService.checkByPassword(id, boardRequestDto.getPassword())==true){
                return ResponseEntity.ok().body(boardService.updateBoard(id, boardRequestDto));
            }else{
                throw new BoardUpdateException("틀린 비밀번호 입니다.");
            }
        }catch (RuntimeException e){
            throw new BoardUpdateException("존재하지 않은 ID 값 입니다.");
        }
    }
// is -> 보통 상태 넘김

    @GetMapping("/list")
    public ResponseEntity<List<BoardResponseDto>> getBoardList() {
        List<BoardResponseDto> getBoardList = boardService.findAll();
        return ResponseEntity.ok().body(getBoardList);
    }
}
