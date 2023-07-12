<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container-fluid mt-3">
    <button class="btn btn-primary" onclick="history.back()">돌아가기</button>
    <c:if test="${board.user.id == principal.user.id}">
    <a href="/board/${board.id}/update-form"><button class="btn btn-info">수정</button></a>
    <button class="btn btn-info" id="btn-delete">삭제</button>
    </c:if>
    <hr />
    <div>
        글 번호: <span id="id"><i>${board.id} </i></span>
        작성자: <span><i>${board.user.username} </i></span>
    </div>
    <hr />
    <div class="form-group">
        <h3>${board.title}</h3>
    </div>
    <hr />
    <div class="form-group">
      <div>${board.content}</div>
    </div>

	<div class="card">
	    <form>
	        <input type="hidden" id="userId" value="${principal.user.id}" />
		    <input type="hidden" id="boardId" value="${board.id}" />
			<div class="card-body">
				<textarea id="reply-content" class="form-control" rows="1"></textarea>
			</div>
			<div class="card-footer">
				<button type="button" id="btn-reply-save" class="btn btn-primary btn-sm">등록</button>
			</div>
		</form>
	</div>

    <div class="card">
        <div class="card-header">댓글 리스트</div>
        <ul id="reply-box" class="list-group">
            <c:forEach var="reply" items="${board.replies}">

                <li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between" style="height:50px;">
                    <div class="d-flex">
                    <div>${reply.content}</div>
                    <c:if test="${reply.user.id eq principal.user.id}">
                        <button onClick="index.deleteReply(${board.id}, ${reply.id})" class="btn btn-primary btn-sm" style="margin-left: 1.25rem; padding: 0.125rem 0.25rem;">삭제</button>
                    </c:if>
                    </div>
                        <div class="font-italic">작성자 : ${reply.user.username}</div>
                </li>

            </c:forEach>
        </ul>
    </div>

</div>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>