package filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import shop.dao.UserRepository;
import shop.dto.PersistentLogin;
import shop.dto.User;


public class LoginFilter extends HttpFilter implements Filter {
    
	/**
	 * - 쿠키 정보 “rememberMe”, “token”을 가져와 변수에 저장한다. 
	 * - 쿠키 정보 “rememberMe”, “token” 가 모두 존재하는 경우, 자동 로그인을 설정한경우로 판단한다. 
	 * - 자동 로그인을 설정한 경우, 테이블 [persistent_logins] 에서 해당 token을 조건으로 
	 *   login_id를 조회하여 session 에 “loginId” 라는 속성명으로 등록한다.
	 */
	private static final long serialVersionUID = 1L;
	
	// UserDAO 선언
	UserRepository userDAO;
	
	// 초기화시
	public void init(FilterConfig fConfig) throws ServletException {
		// 생성
		userDAO = new UserRepository();
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// 쿠키 변수 생성
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Cookie[] cookies = httpRequest.getCookies();
		
		// 쿠키 정보를 담을 변수 선언
		String rememberMe = null;
		String token = null;
		
		// 쿠키 읽어오기
		if( cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				String cookieValue = URLDecoder.decode( cookie.getValue(), "UTF-8");
				// 쿠키 정보 “rememberMe”, “token”을 가져와 변수에 저장한다. 
				switch (cookieName) {
					case "rememberMe"	: rememberMe = cookieValue;  break;
					case "token"		: token = cookieValue;  break;
				}
			}
		}
		
		// 로그인 여부 확인
		HttpSession session = httpRequest.getSession();
		String loginId = (String) session.getAttribute("loginId");
		User loginUser = (User) session.getAttribute("loginUser");
		System.out.println("LoginFilter...");
		System.out.println("rememberMe : " + rememberMe);
		System.out.println("token : " + token);
		
		// 이미 로그인 됨
		if( loginId != null && loginUser != null ) {
			chain.doFilter(request, response);
			System.out.println("로그인된 사용자 : " + loginId);
			return;
		}
		// 자동 로그인 & 토큰 ok
		if( rememberMe != null && token != null ) {
			PersistentLogin plogin = userDAO.selectTokenByToken(token);
			
			// 토큰이 존재하고 유효(만료시간)한지 확인
			if( plogin != null ) {
				String userId= plogin.getUserId();
				loginUser = userDAO.getUserById(userId); 
				// 로그인 처리
				session.setAttribute( "loginId" ,  loginUser.getId() );
				session.setAttribute( "loginUser" , loginUser );
			}
		}
		// 다음 필터로 넘겨줌
		chain.doFilter(request, response);
	}

	public LoginFilter() {
		super();
    }
	// 소멸시
    public void destroy() {
    	
    }
}
