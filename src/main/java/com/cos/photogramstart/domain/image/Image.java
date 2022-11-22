package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {//연관관계 N, 1

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private int id;
    private String caption; // 오늘 나 너무 피곤해!!
    private String postImageUrl;// 사진을 전송받아서 그 사진을 섭어에 특정 폴더에 저장 - DB에 그 저장된 경로를 insert

    @JsonIgnoreProperties({"images"}) // user 정보의 images 까지 필요없다.
    @JoinColumn(name="userId")
    @ManyToOne(fetch = FetchType.EAGER) // 이미지를 select하면 조인해서 User 정보를 같이 들고옴
    private User user;//연관관계 1, 1

    // 이미지 좋아요.

    // 댓글


    private LocalDateTime createDate;

    @PrePersist //디비에 INSERT 되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }

//오브젝트를 콘솔에 출력할 때 문제가 될 수 있어서 User 부분을 출력되지 않게 함.
//    @Override
//    public String toString() {
//        return "Image{" +
//                "id=" + id +
//                ", caption='" + caption + '\'' +
//                ", postImageUrl='" + postImageUrl + '\'' +
//                //", user=" + user + 삭제!!!
//                ", createDate=" + createDate +
//                '}';
//    }
}
