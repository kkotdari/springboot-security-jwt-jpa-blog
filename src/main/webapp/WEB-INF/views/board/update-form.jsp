<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container-fluid mt-3">
  <form>
    <input type="hidden" id="id" value="${board.id}" />
    <div class="mb-3 mt-3">
      <input type="text" class="form-control" id="title" value="${board.title}" required />
    </div>
    <div class="form-group">
      <textarea class="form-control summernote" id="content" required>${board.content}</textarea>
    </div>
  </form>
  <button id="btn-update" class="btn btn-primary">글 수정 완료</button>
</div>

<script>
  $('.summernote').summernote({
    tabsize: 2,
    height: 300
  });
</script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>