package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscrobeRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional(readOnly = true)
    public UserProfileDto 회원프로필(int pageUserId, int principalId){

        UserProfileDto dto = new UserProfileDto();

        //SELECT * FROM image WHERE userId=:userId
        User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });
        //System.out.println("=================================================");

        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId==principalId);//true은 페이지 주인, false 은 주인이 아님
        dto.setImageCount(userEntity.getImages().size());


        int subscribeState = subscrobeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subscrobeRepository.mSubscribeCount(pageUserId);

        dto.setSubscribeState(subscribeState==1);
        dto.setSubscribeCount(subscribeCount);

        return dto;
    }

    @Transactional
    public User 회원수정(int id, User user){
        // 1. 영속화
        // 1. 무조건 찾았다. 걱정마 get() 2. 못찾았어 익섹션 발동시킬께 orElseThrow()

        //글로벌 익셉션을 이용 CustomValidationApiException을 공동으로 사용하겠다.
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            return new CustomValidationApiException("찾을 수 없는 id입니다."); //메세지처리만하면되 그렇다면 CustomValidationApiException 메세지 처리 생성자 하나 추가하면되겠넹
        });

        //람다식으로 변경해자
//        User userEntity = userRepository.findById(10).orElseThrow(() -> {
//                return new IllegalArgumentException("찾을 수 없는 id입니다.");
//        });

//        User userEntity = userRepository.findById(10).orElseThrow(new Supplier<IllegalArgumentException>(){
//            @Override
//            public IllegalArgumentException get() {
//                return new IllegalArgumentException("찾을 수 없는 id입니다.");
//            }
//        });



        // 2. 영속화된 오프벡트를 수정 - 더티체킹 (업데이트 완료)
        userEntity.setName(user.getName());

        String rawPassword = user.getPassword();
        String endPassword = bCryptPasswordEncoder.encode(rawPassword);
        userEntity.setPassword(endPassword);

        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    } //더티체킹이 일어나서 업데이크가 완료됨
}
