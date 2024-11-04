<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="userDAO" class="shop.dao.UserRepository" />

<%
    // 클라이언트에서 전달된 id 파라미터를 받아 삭제할 사용자 ID로 사용
    String userId = request.getParameter("id");

    // 삭제 결과를 저장할 변수
    int result = 0;

    try {
        // 사용자 삭제 실행
        result = userDAO.delete(userId);
        
        if (result > 0) {
            // 세션에서 로그인 정보를 제거
            session.removeAttribute("loginId");

            // 자동 로그인 쿠키 제거
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("autoLoginToken".equals(cookie.getName())) {
                        cookie.setMaxAge(0); // 쿠키 만료 설정
                        response.addCookie(cookie);
                    }
                }
            }

            // 자동 로그인 토큰 데이터베이스에서도 삭제
            userDAO.deleteToken(userId);

            // 성공 시 complete.jsp로 리다이렉트 msg=3 전달
            response.sendRedirect("complete.jsp?msg=3");
        } else {
            // 삭제 실패 시 경고 메시지 출력 후 이전 페이지로 이동
            out.println("<script>alert('회원 삭제에 실패했습니다.'); history.back();</script>");
        }
        
    } catch (Exception e) {
        e.printStackTrace();
        out.println("<script>alert('오류가 발생했습니다. 다시 시도해주세요.'); history.back();</script>");
    }
%>
