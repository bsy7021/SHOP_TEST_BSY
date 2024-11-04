<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Shop</title>
	<jsp:include page="/layout/meta.jsp" /> <jsp:include page="/layout/link.jsp" />
</head>
<body>   
	<% 
	String root = request.getContextPath(); 
	String msg = request.getParameter("msg");
	String message = ""; // 메시지를 저장할 변수

	// 로그인 ID 세션에서 가져오기
	String loginId = (String) session.getAttribute("loginId");
	
	// msg 값에 따른 메시지 설정
	if ("0".equals(msg) && loginId != null) {  // msg 값 비교 시 equals 사용
		message = loginId + "님, 환영합니다.";
	} else if ("1".equals(msg)) {
		message = "회원 가입이 완료되었습니다.";
	} else if ("2".equals(msg)) {
		message = "회원 정보가 수정되었습니다.";
	} else if ("3".equals(msg)) {
		message = "회원 정보가 삭제되었습니다.";
	} else {
		message = "알 수 없는 요청입니다.";
	}
	%>
	<jsp:include page="/layout/header.jsp" />
	<div class="px-4 py-5 my-5 text-center">
		<h1 class="display-5 fw-bold text-body-emphasis">회원 정보</h1>
	</div>
	<!-- 회원 가입/수정/탈퇴 완료 -->
	<div class="container mb-5 text-center">
		<p class="mt-4 d-flex justify-content-center fs-2"><%= message %></p>
		<a href="<%= root %>/index.jsp" type="button" class="mt-4 btn btn-primary">메인화면</a>
	</div>
	
	
	<jsp:include page="/layout/footer.jsp" />
	<jsp:include page="/layout/script.jsp" />
</body>
</html>







