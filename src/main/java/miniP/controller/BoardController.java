package miniP.controller;


import lombok.RequiredArgsConstructor;
import miniP.dto.board.BoardRequestDto;
import miniP.dto.board.BoardResponseDto;
import miniP.entity.Board;
import miniP.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/posts")
    public ResponseEntity<BoardResponseDto> save(@Valid @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return ResponseEntity.status(201).body(boardService.save(boardRequestDto, request));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BoardResponseDto> getOne(@PathVariable("id") Long id) {
        BoardResponseDto resultData= boardService.getOne(id);
        return ResponseEntity.ok().body(resultData);
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteOne(@PathVariable("id") Long id, HttpServletRequest request) {
        boardService.deleteOne(id,request);
        return HttpStatus.OK;
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable("id") Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        BoardResponseDto data = boardService.updateBoard(id, boardRequestDto,request);
        return ResponseEntity.status(201).body(data);
    }


    @GetMapping("/list")
    public ResponseEntity<List<BoardResponseDto>> getBoardList() throws RuntimeException{
        List<BoardResponseDto> getBoardList = boardService.findAll();
        return ResponseEntity.ok().body(getBoardList);
    }

}
