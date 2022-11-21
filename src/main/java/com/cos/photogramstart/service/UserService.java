package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
