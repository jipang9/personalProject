//package miniP.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import miniP.dto.board.BoardRequestDto;
//import miniP.dto.board.BoardResponseDto;
//import miniP.entity.Board;
//import miniP.exception.BoardDeleteException;
//import miniP.exception.BoardUpdateException;
//import miniP.exception.NotExistMemberException;
//import miniP.exception.PostException;
//import miniP.repository.BoardRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class BoardService {
//
//    private final BoardRepository boardRepository;
//
//    @Transactional
//    public BoardResponseDto save(BoardRequestDto boardRequestDto) throws Exception {
//        try {
//            Board board = dtoToEntity(boardRequestDto);
//            boardRepository.save(board);
//            BoardResponseDto boardResponseDto = entityToDto(board);
//            return boardResponseDto;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new PostException();
//        }
//    }
//
//    public BoardResponseDto getOne(Long id) throws Exception{
//        try {
//            Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));
//            BoardResponseDto boardResponseDto = entityToDto(board);
//            return boardResponseDto;
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//            throw new NotExistMemberException();
//        }
//    }
//
//    private Board dtoToEntity(BoardRequestDto boardRequestDto) {
//        Board board = Board.builder()
//                .content(boardRequestDto.getContent())
//                .title(boardRequestDto.getTitle())
//                .build();
//        return board;
//    }
//
//    private BoardResponseDto entityToDto(Board board) {
//        BoardResponseDto boardResponseDto = BoardResponseDto.builder()
//                .content(board.getContent())
//                .title(board.getTitle())
//                .createDate(board.getCreateDate())
//                .modDateTime(board.getModDate())
//                .build();
//        return boardResponseDto;
//    }
//
//    @Transactional
//    public void deleteOne(Long id, String password) {
//        try {
//            Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 게시물입니다."));
//            if (board.checkByPassword(password))
//                boardRepository.deleteById(id);
//            else{
//                throw new BoardUpdateException("비밀번호 불일치");
//            }
//        } catch (Exception e) {
////            e.printStackTrace();
//            throw new BoardDeleteException();
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public List<BoardResponseDto> findAll() {
//        // 1). 직접 @Query 사용해서 만들수 있음, 2).단어 조합하면 알아서 만들어 줌, 3).Sort 메서드 이용 가능
//        List<Board> boardList = boardRepository.findAllByDateDesc();
//        List<BoardResponseDto> boardResponseDtos = new ArrayList<>();
//        boardList.forEach(entity -> {
//            BoardResponseDto boardResponseDto = entityToDto(entity);
//            boardResponseDtos.add(boardResponseDto);
//        });
//        return boardResponseDtos;
//    }
//
//    @Transactional
//    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto) {
//        try {
//            Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 id"));
//            if(board.checkByPassword(boardRequestDto.getPassword())) {
//                board.updateBoard(boardRequestDto);
//                BoardResponseDto boardResponseDto = entityToDto(board);
//                boardRepository.save(board);
//                return boardResponseDto;
//            }else{
//                throw new BoardUpdateException("비밀번호가 일치하지 않습니다.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new BoardUpdateException();
//        }
//    }
//}
