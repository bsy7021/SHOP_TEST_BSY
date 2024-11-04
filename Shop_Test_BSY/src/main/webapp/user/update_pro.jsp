<%@page import="shop.dao.UserRepository"%>
<%@page import="shop.dto.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="userDAO" class="shop.dao.UserRepository" />
<%
	// 객체 생성
	User user = new User();
	// request 에 담아온 정보 추출해서 변수에 저장
	String id = request.getParameter("id");
	String name = request.getParameter("name");
	String gender = request.getParameter("gender");
	String birth = request.getParameter("year");
	birth += "/" + request.getParameter("month");
	birth += "/" + request.getParameter("day");
	String email = request.getParameter("email1");
	email += "@" + request.getParameter("email2");
	String phone = request.getParameter("phone");
	String address = request.getParameter("address");
	
	user.setId(id);
	user.setName(name);
	user.setGender(gender);
	user.setBirth(birth);
	user.setMail(email);
	user.setPhone(phone);
	user.setAddress(address);
	
	// DAO 생성
	UserRepository UserRepository = new UserRepository();
    int result = UserRepository.update(user);
    
	// 회원 정보 수정 처리
    if ( result > 0 ){
        response.sendRedirect("complete.jsp?msg=2");
    } else {
        response.sendRedirect("update.jsp");
    }

%>
