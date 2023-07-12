let index = {
    init: function(){
        // on(event, do)
        $("#btn-register-user").on("click", ()=>{
            this.registerUser();
        });
        $("#btn-update-user").on("click", ()=>{
            this.updateUser();
        });
    },
    registerUser: function(){
        let username = $("#username").val();
        let password = $("#password").val();
        let password2 = $("#password2").val();
        let email = $("#email").val();
        if (password != password2) {
            alert("비밀번호가 일치하지 않습니다");
        } else {
            $.ajax({
                type: "POST",
                url: "/api/v1/users/auth",
                data: JSON.stringify({
                         "username": username,
                         "password": password,
                         "email": email
                      }),
                contentType: "application/json; charset=utf-8", // 요청 body 데이터 타입(MIME 방식)
                dataType: "json" // 서버에서 응답하는 문자열의 형태가 json이면 javascript object로 변경해 줌.
            }).done(function(res){
                if(res.status === 500){
                    alert("회원 가입에 실패하였습니다.");
                } else {
                    alert("회원 가입이 완료되었습니다.");
                }
                console.log(res);
                location.href="/";
            }).fail(function(error){
                alert("JSON.stringify(error)");
            });
        }
    },
    updateUser: function(){
        let userId = $("#user-id").val();
        let username = $("#username").val();
        let password = $("#password").val();
        let password2 = $("#password2").val();
        let email = $("#email").val();
        if (password != password2) {
            alert("비밀번호가 일치하지 않습니다");
        } else {
            $.ajax({
                type: "PUT",
                url: "/api/v1/users/",
                data: JSON.stringify({
                         "id": userId,
                         "username": username,
                         "password": password,
                         "email": email
                      }),
                contentType: "application/json; charset=utf-8", // 요청 body 데이터 타입(MIME 방식)
                dataType: "json" // 서버에서 응답하는 문자열의 형태가 json이면 javascript object로 변경해 줌.
            }).done(function(res){
                alert("회원 정보 수정이 완료되었습니다.");
                console.log(res);
                location.href="/";
            }).fail(function(error){
                alert("JSON.stringify(error)");
            });
        }
    }
}

index.init();