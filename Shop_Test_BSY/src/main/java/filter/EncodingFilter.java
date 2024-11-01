package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;

/**
 * Servlet Filter implementation class EoncodingFilter
 */
// 필터 : 일정 경로(주소)에 진입시 실행되는 클래스
@WebFilter(urlPatterns = { "/*" },	// 모든 경로 URL 매핑
		initParams = {			 	// name = 변수명 value = 값 description = 설명
				@WebInitParam(name = "encoding", value = "UTF-8", description = "인코딩 설정 값") })	// 어노테이션 변수선언
public class EncodingFilter extends HttpFilter implements Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 */

	// 매개 변수 : '둘 이상의 사물의 관계를 맺어주는 것'
	// 매개변수 선언 시 객체 → 변수명 순서
	private FilterConfig filterConfig = null;	// FilterConfig 객체 선언
	private String encoding;					// 변수 선언

	// 생성자
	public EncodingFilter() {
		super();
	}
	
	// 실행 시
	public void init(FilterConfig filterConfig) throws ServletException {	// 필터 실행 시 자동실행
		this.filterConfig = filterConfig;									// FilterConfig 객체를 매개변수에 저장 
		this.encoding = filterConfig.getInitParameter("encoding");			// 인코딩 문자열 어노테이션 변수 파라미터값에서 추출
	}

	// doGet, doPost ...... doFilter
	// 전송 타입 : Filter -> doFilter
	// ex) 전송 타입 : Post -> doPost
	// 필터링 시작
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("인코딩 필터 : " + encoding);
		
		request.setCharacterEncoding(encoding);		// request -> 요청 객체에 charset(문자열 인코딩) UTF-8 설정
		response.setCharacterEncoding(encoding);	// response -> 전송 객체에 charset(문자열 인코딩) UTF-8 설정
		chain.doFilter(request, response);			// 필터링 전에 요청했던 경로로 이동
	}
	
	// 해당 필터링이 소멸시 실행될 때
	public void destroy() {
	}
}
