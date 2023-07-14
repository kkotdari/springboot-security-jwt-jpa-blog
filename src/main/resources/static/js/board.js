function deleteReply(replyId){
    let boardId = $("#board-id").val();
    $.ajax({
        type: "DELETE",
        url: "/boards/" + boardId + "/replies/" + replyId,
    }).done(function(fragment){
        alert("댓글 삭제가 완료되었습니다.");
        console.log("deleteReply 성공, reply-list 통신 성공!");
        document.getElementById("reply-list").innerHTML = fragment;
    }).fail(function(error){
        alert(JSON.stringify(error));
    });
}

$(document).ready(function(){
    // 초기 세팅
    let boardId = $("#board-id").val();
    $.ajax({
      type: "GET",
      url: "/boards/" + boardId + "/replies",
      success: function (fragment) {
          console.log("reply-list 통신 성공!");
          document.getElementById("reply-list").innerHTML = fragment;
      },
      error: function (err) {
          console.log("요청실패", err);
      }
    });

    init();

    function init(){
        // on(event, do)
        $("#btn-save-board").on("click", ()=>{
            saveBoard();
        });
        $("#btn-update-board").on("click", ()=>{
            updateBoard();
        });
        $("#btn-delete-board").on("click", ()=>{
            deleteBoard();
        });
        $("#btn-save-reply").on("click", ()=>{
            saveReply();
        });
    }

    function saveBoard(){
        let userId = $("#user-id").val();
        let title = $("#title").val();
        let content = $("#content").val();
        let data = {
            userId: userId,
            title: title,
            content: content
        };
        $.ajax({
            type: "POST",
            url: "/api/v1/boards",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8", // 요청 body 데이터 타입(MIME 방식)
            dataType: "json" // 서버에서 응답하는 문자열의 형태가 json이면 javascript object로 변경해 줌.
        }).done(function(res){
            alert("글 쓰기가 완료되었습니다.");
            console.log(res);
            location.href="/boards";
        }).fail(function(error){
            alert("JSON.stringify(error)");
        });
    }

    function updateBoard(){
        let boardId = $("#board-id").val();
        let title = $("#title").val();
        let content = $("#content").val();
        let data = {
            title: title,
            content: content
        };
        $.ajax({
            type: "PUT",
            url: "/api/v1/boards/" + boardId,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8", // 요청 body 데이터 타입(MIME 방식)
            dataType: "json" // 서버에서 응답하는 문자열의 형태가 json이면 javascript object로 변경해 줌.
        }).done(function(res){
            alert("글 수정이 완료되었습니다.");
            console.log(res);
            location.href="/boards/" + boardId;
        }).fail(function(error){
            alert("JSON.stringify(error)");
        });
    }

    function deleteBoard(){
        let boardId = $("#board-id").val();
        $.ajax({
            type: "DELETE",
            url: "/api/v1/boards/" + boardId,
            dataType: "json" // 서버에서 응답하는 문자열의 형태가 json이면 javascript object로 변경해 줌.
        }).done(function(res){
            alert("삭제가 완료되었습니다.");
            console.log(res);
            location.href="/boards";
        }).fail(function(error){
            alert("JSON.stringify(error)");
        });
    }

    function saveReply(){
        let data = {
            userId: $("#user-id").val(),
            boardId: $("#board-id").val(),
            content: $("#reply-content").val()
        };
        $.ajax({
            type: "POST",
            url: "/boards/" + data.boardId + "/replies",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
        }).done(function(fragment){
            alert("댓글 작성이 완료되었습니다.");
            console.log("saveReply 성공, reply-list 통신 성공!");
            $("#reply-content").val("");
            document.getElementById("reply-list").innerHTML = fragment;
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
})