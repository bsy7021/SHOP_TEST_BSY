<!-- 로그인 처리 -->
<%@page import="java.util.UUID"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="shop.dto.User"%>
<%@page import="shop.dao.UserRepository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	String rememberMe = request.getParameter("remember-me");
	String rememberId = request.getParameter("remember-id");
	
	UserRepository userDAO = new  UserRepository();
	User loginUser = userDAO.login(id, pw);
	
	// 로그인 실패
	if(loginUser == null){
		response.sendRedirect("login.jsp?error=0");		
	}
	
	// 로그인 성공
	// - 세션에 아이디 등록
	session.setAttribute("loginId", id);
	session.setAttribute("loginUser", loginUser);
	// 아이디 저장
	Cookie cookieRememberId = new Cookie("rememberId","");
	Cookie cookieUserid		= new Cookie("userId","");
	if( rememberId != null && rememberId.equals("on")){
		cookieRememberId.setValue( URLEncoder.encode(rememberId, "UTF-8") );
		cookieUserid.setValue( URLEncoder.encode(id, "UTF-8") );
	}
	
	// 자동 로그인
	Cookie cookieRememberMe	= new Cookie("rememberMe","");
	Cookie cookieToken = new Cookie("token", "");
	cookieRememberMe.setPath("/");
	cookieToken.setPath("/");
	// 쿠키 만료시간 설정 - 7일 (/초)
	cookieRememberMe.setMaxAge(60*60*24*7); 
	cookieToken.setMaxAge(60*60*24*7);
	if( rememberMe != null && rememberMe.equals("on") ) {
		// 자동 로그인 체크 시
		// - 토큰 발행
		String token = null;
		token = userDAO.refreshToken(loginUser.getId());
		// - 쿠키 생성
		cookieRememberMe.setValue( URLEncoder.encode( rememberMe, "UTF-8" ) );
		cookieToken.setValue( URLEncoder.encode( token, "UTF-8" ) );
	}
	// 쿠키 전달
	response.addCookie(cookieRememberId);
	response.addCookie(cookieUserid);
	response.addCookie(cookieRememberMe);
	response.addCookie(cookieToken);
	
	// 로그인 성공 페이지로 이동
	response.sendRedirect("complete.jsp?msg=0");		

%>
