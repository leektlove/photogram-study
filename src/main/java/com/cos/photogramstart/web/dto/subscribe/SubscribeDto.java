package com.cos.photogramstart.web.dto.subscribe;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
    private int id;
    private String username;
    private String profileimageurl;

    //Mariadb가  int를 인식못함 Integer로~
    private Integer subscribestate;
    private Integer equaluserstate;//로그인 사용자와 동일인인지 구분

}

