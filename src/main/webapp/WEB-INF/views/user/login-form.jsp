<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container-fluid mt-3">
  <form action="/auth/login-proc" method="post">
    <div class="mb-3 mt-3">
      <label for="username" class="form-label">Username</label>
      <input type="text" class="form-control" id="username" name="username" placeholder="Enter username" required />
    </div>
    <div class="mb-3">
      <label for="password" class="form-label">Password</label>
      <input type="password" class="form-control" id="password" name="password" placeholder="Enter password" required />
    </div>
    <button type="submit" class="btn btn-primary">로그인하기</button>
    <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=f39050ec53184eddbfff9a9658afaf80&redirect_uri=http://localhost:8080/auth/kakao/callback&response_type=code"><img height="39.33px" src="/image/kakao_login_button.png" /></a>
  </form>
</div>

<%@ include file="../layout/footer.jsp"%>