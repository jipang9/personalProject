package miniP.board.controller;


import lombok.RequiredArgsConstructor;
import miniP.board.dto.BoardRequestDto;
import miniP.board.dto.BoardResponseDto;
import miniP.board.service.BoardServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardServiceImpl boardServiceImpl;

    @PostMapping("/posts")
    public ResponseEntity<BoardResponseDto> save(@RequestBody BoardRequestDto boardRequestDto) {
        return ResponseEntity.status(201).body(boardServiceImpl.BoardSave(boardRequestDto));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<BoardResponseDto> getOne(@PathVariable("id") Long id) {
        BoardResponseDto resultData= boardServiceImpl.getOne(id);
        return ResponseEntity.ok().body(resultData);
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteOne(@PathVariable("id") Long id) {
        boardServiceImpl.deleteOne(id);
        return HttpStatus.OK;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable("id") Long id, @RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto data = boardServiceImpl.updateBoard(id, boardRequestDto);
        return ResponseEntity.status(201).body(data);
    }


    @GetMapping("/lists")
    public ResponseEntity<List<BoardResponseDto>> getBoardList() throws RuntimeException{
        List<BoardResponseDto> getBoardList = boardServiceImpl.ListAll();
        return ResponseEntity.ok().body(getBoardList);
    }

}
