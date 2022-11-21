package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {

    private boolean pageOwnerState; //isPageOwner JSTL에서 앞에 is 붙으면 제대로 파싱이 안된다. pageOwnerState로 변경
    private int imageCount;
    private User user;

}
