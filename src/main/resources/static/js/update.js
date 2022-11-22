// (1) 회원정보 수정
function update(userId, event) {
    //console.log(event)
    //alert("update");

    event.preventDefault();//폼태그 액션을 막기


    //제이쿼리 사용
    let data = $("#profileUpdate").serialize();
    // console.log(data);

    $.ajax({
        type:"put",
        url:`/api/user/${userId}`,
        data: data,
        contentType:"application/x-www-form-urlencoded; charset=utf-8",
        dataType:"json"
    }).done(res=>{ //HttpStatus 상태코드 200번대  ControllerExceptionHandler 에서 제어한다. CMRespDto<?> -> ResponseEntity  로 변경
        console.log("update.js 성공", res);
        location.href=`/user/${userId}`; //console 로그를ㄹ 보기 위해 잠시 주석처리 페이지 이동하면서 안보임

    }).fail(error=>{ //HttpStatus 상태코드 200번대 아닐때
        //alert(error.responseJSON.data.name);

        // console.log("update.js 실패", error);

        if(error.data ==null){
            alert(error.responseJSON.message);
        }else{
            alert(JSON.stringify(error.responseJSON.data));
        }

        // console.log("update.js 실패", error.responseJSON.data);

    });



}