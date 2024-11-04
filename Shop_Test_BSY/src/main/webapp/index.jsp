<%@page import="shop.dao.ProductRepository"%>
<%@page import="java.util.List"%>
<%@page import="shop.dto.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Shop</title>
	<jsp:include page="/layout/meta.jsp" />
	<jsp:include page="/layout/link.jsp" />
</head>
<body>   
	<% 
		// 현재 세션에 저장된 loginId 확인
		String root = request.getContextPath(); 
		String loginId = (String) session.getAttribute("loginId"); 
	%>
	<jsp:include page="/layout/header.jsp" />

	<div class="px-4 py-5 my-5 text-center">
		<h1 class="display-5 fw-bold text-body-emphasis">메인화면</h1>
		<div class="col-lg-6 mx-auto">
			<p class="lead mb-4">Shop 쇼핑몰 입니다.</p>
			<div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
				<a href="<%= root %>/shop/products.jsp" type="button" class="btn btn-primary">상품목록</a>

				<% if (loginId == null) { %>
					<!-- 로그인되지 않은 경우 로그인 버튼 표시 -->
					<a href="<%= root %>/user/login.jsp" type="button" class="btn btn-outline-dark">로그인</a>
				<% } else { %>
					<!-- 로그인된 경우 로그아웃 버튼 표시 -->
					<a href="<%= root %>/user/logout.jsp" type="button" class="btn btn-outline-danger">로그아웃</a>
				<% } %>
			</div>
		</div>
	</div>

	<jsp:include page="/layout/footer.jsp" />
	<jsp:include page="/layout/script.jsp" />
</body>
</html>
