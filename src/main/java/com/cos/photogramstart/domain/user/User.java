package com.cos.photogramstart.domain.user;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

// JPA - Java Persistence API(자바를 데이터로 영구적으로 저장(DB)할수 있는 API 제공)

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity //디비에 테이블을 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private int id;

    @Column(length = 20, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String website; // 웹사이트
    private String bio; // 자기소개
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role;// 권한

    // mappedBy 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지 마!!
    // 연관관계의 주인은 Image 테이블에 user다. 그러므로 테이블에 컬럼을 만들지 마!!
    // User를 Select할 때 해당 User id로 등록된 image들을 다 가져와.

    //     @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    // LAZY = User를 Select할 때 해당 User id로 등록된 image들을 가져오지마. ~ 대신 getImages() 함수가 호출될때 가져와

    //     @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    // EAGER = User를 select할 때 해당 User id로 등록된 image들을 Join해서 가져와.

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"})
    private List<Image> images;//양방향 매핑

    private LocalDateTime createDate;

    @PrePersist // 디비에 INSERT 되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }
}
