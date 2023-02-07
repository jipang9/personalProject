package miniP.recommend.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import miniP.board.entity.Board;
import miniP.board.service.BoardService;
import miniP.comment.entity.Comment;
import miniP.comment.service.CommentService;
import miniP.exception.ExceptionStatus;
import miniP.exception.member.CustomException;
import miniP.member.entity.Member;
import miniP.recommend.entity.BoardRecommend;
import miniP.recommend.repository.BoardRecommendRepository;
import miniP.recommend.repository.CommentRecommendRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    private final BoardService boardService;
    private final BoardRecommendRepository boardRecommendRepository;
    private final CommentRecommendRepository commentRecommendRepository;
    private final CommentService commentService;


    @Override
    @Transactional
    public void upRecommendByBoard(Long id, Member member) {
        Board board = boardService.findBoard(id);
        Optional<BoardRecommend> data = boardRecommendRepository.findByMemberAndBoard(board.getId(), member.getId());
        if (data.isEmpty() == true) {
            BoardRecommend boardRecommend = BoardRecommend.builder().board(board).member(member).build();
            boardRecommendRepository.save(boardRecommend);
            board.addRecommendCount(); // 이건 트랜잭션에 의존적이다..
        } else
            throw new CustomException(ExceptionStatus.USER_RECOMMEND_IS_EXIST);
    }


    @Override
    @Transactional
    public void downRecommendByBoard(Long id, Member member) {
        Board board = boardService.findBoard(id);
        BoardRecommend data = boardRecommendRepository.findByMemberAndBoard(board.getId(), member.getId()).get();
        boardRecommendRepository.delete(data);
        board.minusRecommendCount(); // 이건 트랜잭션에 의존적이다..
    }

    @Override
    public void upRecommendByComment(Long id, Member member) {
        Comment comment = commentService.getComment(id);
    }

}
