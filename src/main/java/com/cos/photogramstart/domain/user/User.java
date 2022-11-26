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
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private int id;

    @Column(length = 100, unique = true, nullable = false)// OAuth2 로그인을 위해 컬럼 늘리기
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

    private String profileimageurl;
    private String role;// 권한

    private LocalDateTime createdate;
    @PrePersist  // 1-13. DB에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createdate = LocalDateTime.now();
    }

    // mappedBy 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지 마!!
    // 연관관계의 주인은 Image 테이블에 user다. 그러므로 테이블에 컬럼을 만들지 마!!
    // User를 Select할 때 해당 User id로 등록된 image들을 다 가져와.

    //     @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    // LAZY = User를 Select할 때 해당 User id로 등록된 image들을 가져오지마. ~ 대신 getImages() 함수가 호출될때 가져와

    //     @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    // EAGER = User를 select할 때 해당 User id로 등록된 image들을 Join해서 가져와.

    // 1-14. image 엔티티 양방향 매핑 mappedBy 속성의 의미는 나는 연관관계의 주인이 아니므로 DB에 테이블을 만들지 말라는 뜻.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // 1-15. Lazy 전략은 image를 get할때만 같이 select 하라는 의미.
    @JsonIgnoreProperties({"user"}) // 1-16. image 엔티티에 있는 user는 또 불러오지 않는다.(무한참조)
    private List<Image> images;//양방향 매핑


    // 1-15. AOP 처리시 User 객체 따로 sysout 해보고 싶을 때 toString 커스터마이징(images 제거)
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", profileimageurl='" + profileimageurl + '\'' +
                ", role='" + role + '\'' +

                ", createdate=" + createdate +
                '}';
    }
}

//java.sql.SQLException: Unknown column 'create_date' in 'field list'
// private LocalDateTime createdate;
//", createDate=" + createDate +   ->   ", createdate=" + createdate +


//java.sql.SQLException: Unknown column 'profile_image_url' in 'field list'
//private String profileimageurl;