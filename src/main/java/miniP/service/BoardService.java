package miniP.service;

import io.jsonwebtoken.Claims;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.dto.board.BoardRequestDto;
import miniP.dto.board.BoardResponseDto;
import miniP.entity.Board;
import miniP.entity.Member;
import miniP.exception.board.BoardSaveException;
import miniP.jwt.JwtUtil;
import miniP.repository.BoardRepository;
import miniP.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;



@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    // 여기 save 로직에서는 결국 헤더에 있는 token속에 있는 정보를 이용해서 user 정보를 받고, 그 정보를 기반으로 데이터를 저장해야 한다. -> 그리고 게시글을 반환하다.

    // 생각한 것 -> 우선 게시글을 작성하기 위해선 헤더에 있는 토큰으로 정보를 뽑아내야한다.
    // 이 때, 과연 이 정보를 뽑아내는 로직을 어디에서 선언할 것인가? -> 최초 : boardService => 현재 : jwtUtil
    // 이유는 굳이 정보를 뽑아내는 과정을 boardService에서 관리할 필요가 없으니까?

    // 게시글 저장
    @Transactional
    public BoardResponseDto save(BoardRequestDto boardRequestDto, HttpServletRequest request)  {
        try {
            String username = jwtUtil.validateToken(request);
//            String username2 = validateToken(request);
            Member member = memberRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다"));
            Board board = boardRequestDto.toEntity(boardRequestDto, member);
            boardRepository.save(board);
            BoardResponseDto b = BoardResponseDto.toDto(board);
            return b;
        } catch (RuntimeException e) {
            throw new BoardSaveException();
        }
    }


    // 매개변수로 id 값이 넘어오는데, 이 id 값은 board의 PK값인 id를 의미한다.

//    // 게시물 조회
////    @Transactional(readOnly = true)
////    public BoardResponseDto getOne(Long id) thorws RuntimeException {
////        try {
////            Board getBoard = boardRepository.findById(id).orElseThrow(() -> new RuntimeException(" 게시물을 찾을 수 없습니다 "));
////
////            BoardResponseDto boardResponseDto = b.toDto(getBoard);
////
////
////            return boardResponseDto;
////        } catch (RuntimeException e) {
////            throw new NotFoundBoardException();
//        }
//    }

    // 파라미터로 삭제하고자 하는 게시물의 번호화 http 데이터가 들어오는데
    // -> 이 데이터 속에서 사용자 정보를 가지고와서 -> 그것을 가지고 게시물에 관한 검증이 필요함 -> 삭제 진행
    // 게시물 단건 삭제 로직 중 if문 속 조건을 분리할 필요가 있을까? -> update에도 동일하게 사용함

////     게시물 삭제
//    @Transactional
//    public void deleteOne(Long id, HttpServletRequest request) throws RuntimeException{
//        try {
//            Board board = boardRepository.findById(id).orElseThrow(()-> new NotFoundBoardException(" 게시물을 찾을 수 없습니다 "));
//            String username = jwtUtil.validateToken(request);
//            if (board.getMember().getUsername().equals(username))
//                boardRepository.deleteById(id);
//            else
//                throw new IllegalStateException(" 작성자가 아닙니다 ");
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new BoardDeleteException();
//
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public List<BoardResponseDto> findAll() throws RuntimeException{
//        Board board = null;
//        List<Board> boardList = boardRepository.findAllByDateDesc();
//        List<BoardResponseDto> boardResponseDtos = new ArrayList<>();
//        boardList.forEach(entity -> {
//            BoardResponseDto boardResponseDto = board.toDto(entity);
//            boardResponseDtos.add(boardResponseDto);
//        });
//        return boardResponseDtos;
//    }
///**
//    // 일단 만들어만 놓자. -> 이렇게 하는 의미가 무엇일까?...
//    // 나는 그냥 중복된 코드는 하나로 묶고싶어서 이 것을 만들긴했는데...( delete 속 기능과 modify 속 검증이 비슷해서? )
//    // 만약에 이 메서드를 만들어서 오버로딩해서 사용한다면? -> delete와 modify가 파라미터가 다르다
////    public boolean CheckUserName(Long id,HttpServletRequest request){
////        try{
////            Board board = boardRepository.findById(id).orElseThrow(()->new NotFoundBoardException(" 존재하지 않는 게시물 "));
////            String username = jwtUtil.validateToken(request);
////            if(board.getMember().getUsername().equals(username)){
////                return true;
////            }else{
////                return false;
////            }
////        }
////        catch (RuntimeException e){
////            throw new IllegalStateException(" 해당 기능이 동작하지 않습니다");
////        }
////    }
//*
// */
//
////     게시물 수정
//    @Transactional
//            public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) throws RuntimeException{
//                try {
//                    Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException(" 존재하지 않는 게시물 "));
//                    String username = jwtUtil.validateToken(request);
//                    board.updateBoard(boardRequestDto);
//                    if (board.getMember().getUsername().equals(username)) {
//                        boardRepository.saveAndFlush(board); // jpa에선 save()를 권장..
//                        BoardResponseDto result = board.toDto(board);
//                        return result;
//                    } else
//                        throw new IllegalStateException("작성자가 아닙니다");
//                }catch (Exception e) {
//                    e.printStackTrace();
//                    throw new BoardUpdateException();
//        }
//}
}
