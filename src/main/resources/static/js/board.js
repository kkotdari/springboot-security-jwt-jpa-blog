$(document).ready(function(){
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
            url: "/api/v1/boards/" + data.boardId + "/replies",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            alert("댓글 작성이 완료되었습니다.");
            location.href = "/boards/" + boardId;
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }

   function deleteReply(boardId, replyId){
        $.ajax({
            type: "DELETE",
            url: "/api/board/" + boardId + "/reply/" + replyId,
            dataType: "json"
        }).done(function(resp){
            alert("댓글 삭제가 완료되었습니다.");
            location.href = "/boards/" + boardId;
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
})