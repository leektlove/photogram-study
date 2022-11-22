package com.cos.photogramstart.domain.subscribe;

import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Locale;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name ="subscribe_uk",
                        columnNames={"fromUserId", "toUserId"}

                )
        }
)
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private int id;

    @JoinColumn(name="fromUserId") //이렇게 컬럼명을 만들어
    @ManyToOne
    private User fromUser; //구독하는애

    @JoinColumn(name="toUserId")
    @ManyToOne
    private User toUser; //구독받는애

    private LocalDateTime createDate;

    @PrePersist //디비에 INSERT 되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }


}
