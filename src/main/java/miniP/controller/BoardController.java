package miniP.controller;


import lombok.RequiredArgsConstructor;
import miniP.dto.board.BoardRequestDto;
import miniP.dto.board.BoardResponseDto;
import miniP.entity.Board;
import miniP.service.BoardService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/posts")
    public ResponseEntity<BoardResponseDto> save(@RequestBody BoardRequestDto boardRequestDto) {
        return ResponseEntity.status(201).body(boardService.save(boardRequestDto));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<BoardResponseDto> getOne(@PathVariable("id") Long id) {
        BoardResponseDto resultData= boardService.getOne(id);
        return ResponseEntity.ok().body(resultData);
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteOne(@PathVariable("id") Long id) {
        boardService.deleteOne(id);
        return HttpStatus.OK;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable("id") Long id, @RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto data = boardService.updateBoard(id, boardRequestDto);
        return ResponseEntity.status(201).body(data);
    }


    @GetMapping("/lists")
    public ResponseEntity<List<BoardResponseDto>> getBoardList() throws RuntimeException{
        List<BoardResponseDto> getBoardList = boardService.findAll();
        return ResponseEntity.ok().body(getBoardList);
    }

}
