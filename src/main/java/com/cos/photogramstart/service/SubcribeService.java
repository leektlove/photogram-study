package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper; //mvnrepository.com 가서 라이브러리 받아라
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubcribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em;// Repository는 Entity

    @Transactional
    public List<SubscribeDto> 구독리스트(int principalId, int pageUserId){

        //쿼리 준비
        StringBuffer sb= new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, "); //마지막 한칸 다 뛰어야함
        sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
        sb.append("if((?=u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?"); // 세미콜론 첨부하면 안됨

        // 1. 물음표(?) principalId
        // 2. 물음표(?) principalId
        // 3. 마지막(?) 물음표 pageUserId

        //쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        //쿼리 실행 (qlrm 라이브러리) Dto에 DB결과를 매핑하기 위해서
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);

        return subscribeDtos;
    }


    @Transactional(readOnly = true)
    public void 구독하기(int fromUserId, int toUserId){
        //subscribeRepository.save(null);
        //int fromUserId, int toUserId 인트값으로 객체를 못만들겠징
        //fromUser toUser는 오브젝트죠

        //네이티브 쿼리로 사용하면 더쉽고 간단하게 할수 있다.  At SubscribeRepository
        try{
            subscribeRepository.mSubscribe(fromUserId, toUserId);
        }catch (Exception e){
            throw new CustomApiException("이미 구독을 하였습니다. ");
        }

    }

    @Transactional
    public void 구독취소하기(int fromUserId, int toUserId){
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }


}
