package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// NotNull = Null값 체크
// NotEmpty = 빈값이거나 null 체크
// NotBlank = 빈값이거나 null 체크 그리고 빈 공백까지

@Data
public class CommentDto {
    @NotBlank // 빈값이거나 null 체크 그리고 빈 공백까지
    private String Content;

    @NotNull  // Null 체크
    private Integer imageId;

    //toEntity 가 필요없다.
}
