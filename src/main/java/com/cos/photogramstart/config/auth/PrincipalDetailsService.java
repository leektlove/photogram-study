package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service //IoC
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 1. 패스워드는 알아서 채킹하니까 신경 쓸 필요 없다.
    // 2. 리턴이 잘되면 자동으로 UserDetail 타입을 세션을 만든다.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //System.out.println("나 실행돼?" + username);

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            return null;
        }else{
//            return userEntity;?
            return new PrincipalDetails(userEntity); //UserDetails 일일이 작성하기 힘드니 PrincipaDetails  작성한다.
        }

    }
}
