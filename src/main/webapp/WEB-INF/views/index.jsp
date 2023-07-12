<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>

<div class="container-fluid mt-3" style="padding-left: 0;">
  <c:forEach var="board" items="${boardPage.content}">
  <div class="card m-2" style="width:100%">
    <div class="card-body">
      <h4 class="card-title">${board.title}</h4>
      <a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
    </div>
  </div>
  </c:forEach>

  <ul class="pagination justify-content-center">
    <c:if test="${pageInfo.hasPrev}">
      <li class="page-item"><a class="page-link" href="/?page=${pageInfo.prevIndex - 1}">Previous</a></li>
    </c:if>

    <c:forEach var="i" begin="${pageInfo.startNumber}" end="${pageInfo.endNumber}" step="1">
      <c:if test="${i -1 != pageInfo.pageNo}">
        <li class="page-item">
      </c:if>
      <c:if test="${i -1 == pageInfo.pageNo}">
        <li class="page-item font-weight-bolder">
      </c:if>
      <a class="page-link" href="/?page=${i - 1}">${i}</a></li>
    </c:forEach>

    <c:if test="${pageInfo.hasNext}">
      <li class="page-item"><a class="page-link" href="/?page=${pageInfo.nextIndex - 1}">Next</a></li>
    </c:if>
  </ul>

</div>

<%@ include file="layout/footer.jsp"%>