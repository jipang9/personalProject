package miniP.recommend.service;

import miniP.member.entity.Member;

public interface RecommendService {

    void upRecommendByBoard(Long id, Member member);

    void downRecommendByBoard(Long id, Member member);

    void upRecommendByComment(Long id, Member member);


}
