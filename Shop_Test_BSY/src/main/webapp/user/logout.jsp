<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	
	// 자동 로그인 토큰 쿠키 삭제
	Cookie rememberMe = new Cookie("rememberMe", "");
	rememberMe.setMaxAge(0); // 쿠키 삭제
	response.addCookie(rememberMe);
	
	// 아이디 저장 관련 쿠키 삭제 (username, rememberId)
	Cookie usernameCookie = new Cookie("loginId", "");
	usernameCookie.setMaxAge(0); // 쿠키 삭제
	response.addCookie(usernameCookie);
	
	Cookie rememberIdCookie = new Cookie("rememberId", "");
	rememberIdCookie.setMaxAge(0); // 쿠키 삭제
	response.addCookie(rememberIdCookie);
	
	// 세션 무효화
	session.invalidate();
	
	// 로그아웃 완료 후 메인 페이지로 리다이렉트
	String root = request.getContextPath();
	response.sendRedirect(root + "/index.jsp");
%>