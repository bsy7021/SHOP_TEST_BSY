package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;


/**
 * Servlet Filter implementation class EncodingFilter
 */
public class EncodingFilter extends HttpFilter implements Filter {
    
	/**
	 * - 필터가 초기화 될 때, web.xml 에서 설정한 초기 파라미터 “encoding”을 가져와멤버변수 encoding(String) 에 초기화한다. 
	 * - 필터가 실행될 때, 요청 객체와, 응답 객체에 문자 인코딩을 멤버변수 encoding 으로 설정하고 다음 필터를 호출한다.
	 */
	private static final long serialVersionUID = 1L;
	
	// 멤버 변수
	private String encoding;
	
    public EncodingFilter() {
        super();
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    	// 파라미터 값을 가져와서 대입 
    	encoding = filterConfig.getInitParameter("encoding");
    }

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("인코딩 필터 : " + encoding);
		
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		
		// 다음 필터 호출
		chain.doFilter(request, response);
	}


	public void destroy() {
	
	}
}
