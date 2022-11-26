package com.cos.photogramstart.config.auth;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private static final long SerialVersionUID = 1L;
    private User user;
    private Map<String, Object> attributes;

    public PrincipalDetails(User user){
        this.user = user;
    }
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }


    //권한 : 한개가 아닐수 있음. (어떤 누군가는 다수 3개 이상일수도 있어)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(() -> {
            return user.getRole();
        });
        return collector;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        //이계정이 만료가 되었니?
//        return user.getExipred();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //이계정이 잠겼니?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //1년이 지난 너의 비번이 바뀌지 않았니?
        return true;
    }

    @Override
    public boolean isEnabled() {
        //니 계정이 활성화 되어있니?
        return true;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attributes;  // {id:341023203102301, name:박종희, email:pjh2688@naver.com}
    }

    @Override
    public String getName() {
        return (String)attributes.get("name");
    }
}
