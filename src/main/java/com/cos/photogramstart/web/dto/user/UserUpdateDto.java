package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateDto {
    @NotBlank
    private String name; //필수
    @NotBlank
    private String password; //필수
    private String email;
    private String website;
    private String bio;
    private String phone;
    private String gender;

    //조금 위험함 코드 수정이 필요함.
    public User toEntity(){
        return User.builder()
                .name(name) //Validation 체크 이름을 기재 안했으면 문제!!!
                .password(password) //Validation 체크 패스워드를 기재 안햇으면 공백으로 들어오면 문제 데이터베이스에 공백 들어가겠죠!!
                .email(email)
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();

    }
}
