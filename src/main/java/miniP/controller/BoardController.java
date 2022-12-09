package miniP.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.dto.BoardRequestDto;
import miniP.dto.BoardResponseDto;
import miniP.exception.BoardDeleteException;
import miniP.exception.BoardUpdateException;
import miniP.exception.PostException;
import miniP.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
// 예외처리에 대한 추가적인 그리고 예외처리를 핸들링하는 부분에 대한 이해가 필요한 것으로 보입니다. (자기성찰-12-09)
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/save")
    public ResponseEntity<BoardResponseDto> save(@RequestBody BoardRequestDto boardRequestDto) throws Exception {
        return ResponseEntity.ok().body(boardService.save(boardRequestDto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BoardResponseDto> getOne(@PathVariable("id") Long id) throws Exception{
        BoardResponseDto result = boardService.getOne(id);
        return ResponseEntity.ok().body(result);
    }


    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteOne(@PathVariable("id") Long id, @RequestParam String password) throws RuntimeException {
        boardService.deleteOne(id, password);
        return HttpStatus.OK;
    }


    @PutMapping("/modify/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable("id") Long id, @RequestBody BoardRequestDto boardRequestDto) throws RuntimeException{
        return ResponseEntity.ok().body(boardService.updateBoard(id, boardRequestDto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<BoardResponseDto>> getBoardList() throws RuntimeException{
        List<BoardResponseDto> getBoardList = boardService.findAll();
        return ResponseEntity.ok().body(getBoardList);
    }
}
