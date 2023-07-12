<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container-fluid mt-3">
  <form>
    <div class="mb-3 mt-3">
      <input type="text" class="form-control" id="title" placeholder="Enter title" required />
    </div>
    <div class="form-group">
      <textarea class="form-control summernote" id="content" placeholder="Enter content" required></textarea>
    </div>
  </form>
  <button id="btn-save" class="btn btn-primary">글 쓰기 완료</button>
</div>

<script>
  $('.summernote').summernote({
    tabsize: 2,
    height: 300
  });
</script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>