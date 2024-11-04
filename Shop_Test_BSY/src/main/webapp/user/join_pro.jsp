<!-- 회원 가입 처리 -->
<%@page import="shop.dao.UserRepository"%>
<%@page import="shop.dto.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	// 객체생성
	User user = new User();
	// request 에 담아온 정보 추출해서 변수에 저장
	String id = request.getParameter("id");
	String password = request.getParameter("pw");
	String name = request.getParameter("name");
	String gender = request.getParameter("gender");
	String birth = request.getParameter("year");
	birth += "/" + request.getParameter("month");
	birth += "/" + request.getParameter("day");
	String email = request.getParameter("email1");
	email += "@" + request.getParameter("email2");
	String phone = request.getParameter("phone");
	String address = request.getParameter("address");
	String regist_day = request.getParameter("regist_day");
	
	user.setId(id);
	user.setPassword(password);
	user.setName(name);
	user.setGender(gender);
	user.setBirth(birth);
	user.setMail(email);
	user.setPhone(phone);
	user.setAddress(address);
	user.setRegistDay(regist_day);
	
	// DAO 생성
	UserRepository userRepository = new UserRepository();
	int result = userRepository.insert(user);
	
	String root = request.getContextPath();
	if( result > 0 ){
		// 로그인 화면으로 이동
		response.sendRedirect(root + "/user/login.jsp");
	}
	else{
		// 회원가입 페이지로 이동
		response.sendRedirect(root + "/user/join.jsp?error");
	}
%>
