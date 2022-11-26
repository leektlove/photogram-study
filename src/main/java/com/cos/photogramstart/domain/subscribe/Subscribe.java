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
        name = "subscribe",
        uniqueConstraints = {
                @UniqueConstraint(
                        name ="subscribe_uk",
                        columnNames={"fromuserid", "touserid"}

                )
        }
)
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private int id;

    @JoinColumn(name="fromuserid") //이렇게 컬럼명을 만들어
    @ManyToOne
    private User fromuser; //구독하는애

    @JoinColumn(name="touserid")
    @ManyToOne
    private User touser; //구독받는애

    private LocalDateTime createdate;

    @PrePersist //디비에 INSERT 되기 직전에 실행
    public void createDate(){
        this.createdate = LocalDateTime.now();
    }


}
