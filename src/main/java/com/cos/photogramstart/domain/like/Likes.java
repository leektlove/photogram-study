package com.cos.photogramstart.domain.like;

import com.cos.photogramstart.domain.image.Image;
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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name ="likes_uk",
                        columnNames={"imageId", "userId"}

                )
        }
)
public class Likes { // N

    // columnNames={"imageId", "userId"}  두개는 중복 유니크 값을 가지면 안된다. image user
    // 1 번 유저가 1번을 좋아 할수 없다.

    //Like는 키워드다 Likes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private int id;

    //무한 참조 해결
    @JoinColumn(name = "imageId")
    @ManyToOne
    private Image image; // 1

    //오류가 터지고 나서 잡아봅시다.  @JsonIgnoreProperties({"images"}) <- 무한 참조 해결 like 안에 images 안에가 안나오면 해결된것임
    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;  // 1


    private LocalDateTime createDate;

    @PrePersist //디비에 INSERT 되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }


}
