<%@page import="shop.dto.Product"%>
<%@page import="java.util.List"%>
<%@page import="shop.dao.OrderRepository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String root = request.getContextPath();
    String phone = request.getParameter("phone");
    String orderPw = request.getParameter("orderPw");

    // 세션에 비회원 주문 내역 조회 정보 저장
    session.setAttribute("orderPhone", phone);
    session.setAttribute("orderPw", orderPw);

    // 주문 내역 페이지로 리다이렉트
    response.sendRedirect(root + "/user/order.jsp");
%>
