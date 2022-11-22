package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service // 1.Ioc 2.트랜잭션 관리
public class AuthService
{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional //Write(insert, Update, Delete)
    public User userSign(User user){
        //회원가입 진행

        String rawPassword = user.getPassword();
        String encPaswword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPaswword);
        user.setRole("ROLE_USER");//관리자는 ROLE_ADMIN
        User userEntity = userRepository.save(user);
        return userEntity;
    }

}
