package miniP.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.board.dto.BoardRequestDto;
import miniP.board.dto.BoardResponseDto;
import miniP.board.dto.BoardsResponseDto;
import miniP.board.entity.Board;
import miniP.board.repository.BoardRepository;
import miniP.comment.dto.CommentResponseDto;
import miniP.comment.service.CommentService;
import miniP.exception.ExceptionStatus;
import miniP.exception.board.NotFoundBoardException;
import miniP.exception.member.CustomException;
import miniP.exception.member.NotExistMemberException;
import miniP.member.entity.Member;
import miniP.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentService commentService;

    @Transactional
    @Override
    public void boardSave(BoardRequestDto boardRequestDto, String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new NotExistMemberException());
        Board board = boardRequestDto.toEntity(member);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true) // 게시물 조회 (단건 - 댓글까지 다 )
    @Override
    public BoardResponseDto getOne(Long id) {
        Board getBoard = boardRepository.findById(id).orElseThrow(() -> new NotFoundBoardException());
        List<CommentResponseDto> comments = commentService.comments(getBoard.getId());
        return BoardResponseDto.of(getBoard, comments);
    }

    @Transactional // 게시물 삭제
    @Override
    public void deleteOne(Long id, Member member) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new NotFoundBoardException()); // 해당 게시물
        board.isWrite(member.getUsername()); // 작성자 체크
        commentService.deleteCommentsByBoard(board.getId()); // comment 삭제
        boardRepository.deleteById(id);
    }

    @Override
    @Transactional // 게시물 삭제 -> board entity를 넘기자.
    public void deleteOneV2(Long id, Member member) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new NotFoundBoardException()); // 해당 게시물
        board.isWrite(member.getUsername()); // 작성자 체크
        commentService.deleteCommentsByBoardV2(board);
        boardRepository.deleteById(id);

    }

    @Transactional(readOnly = true) // 전체 조회 ( 간략한 내용 만 )
    @Override
    public List<BoardsResponseDto> ListAll() {
        List<Board> boardList = boardRepository.findAllByDateDesc();
        List<BoardsResponseDto> getBoard = new ArrayList<>();
        boardList.forEach(o -> {
            BoardsResponseDto boardsResponseDto
                    = new BoardsResponseDto(o, commentService.countCommentByBoardId(o.getId())
            );
            getBoard.add(boardsResponseDto);

        });
        return getBoard;
    }

    @Override
    public List<BoardsResponseDto> ListPaging(Pageable pageable, int page) {
        List<BoardsResponseDto> resultList = new ArrayList<>();
        Page<Board> pages = boardRepository.findAll(pageable.withPage(page));
        for (Board board : pages) {
            Long count = commentService.countCommentByBoardId(board.getId());
            BoardsResponseDto boardsResponseDto = new BoardsResponseDto(board, count);
            resultList.add(boardsResponseDto);
        }
        return resultList;
    }


    @Transactional // 게시물 수정
    @Override
    public void updateBoard(Long id, BoardRequestDto boardRequestDto, Member member) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionStatus.BOARD_IS_NOT_EXIST));
        board.isWrite(member.getUsername());
        board.updateBoard(boardRequestDto.getTitle(), boardRequestDto.getContent());
        boardRepository.save(board);
    }

    @Override
    public Board findBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionStatus.BOARD_IS_NOT_EXIST));
        return board; // 이걸 메소드화 했던 내용을 과거에 관호님이랑 이야기 했었던 것 같은데...
    }

    //    @Override
//    public List<BoardResponseDto> myBoardList(Member member) {
//        List<BoardResponseDto> myboards = boardRepository.findAllByMember(member);
//        return myboards;
//    }


}
