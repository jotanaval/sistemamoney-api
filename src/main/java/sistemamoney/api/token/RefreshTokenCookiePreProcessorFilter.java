package sistemamoney.api.token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import org.springframework.stereotype.Component;

/**
 * @author junior
 * GitHub : jotanaval
 * email: jotanaval2009@gmail.com
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		
		if ("/oauth/token".equalsIgnoreCase(req.getRequestURI())
				&& "refresh_token".equals(req.getParameter("grant_type"))
				&& req.getCookies() != null) {
			for (Cookie cookie : req.getCookies()) {
				if (cookie.getName().equals("refreshToken")) {
					String refreshToken = cookie.getValue();
					req = new MyServiletRequestWrapper(req, refreshToken);
					
					
				}
				
			}
		}
		chain.doFilter(req, response);
		
	}
	static class MyServiletRequestWrapper extends HttpServletRequestWrapper{
		
		private String refreshToken;

		public MyServiletRequestWrapper(HttpServletRequest request, String  refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
			
		}
		@Override
		public Map<String, String[]>getParameterMap(){
			ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
			
			map.put("refreshToken", new String[] {refreshToken});
			map.setLocked(true);
			return map;
		}
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}


@Override
public void destroy() {
	// TODO Auto-generated method stub
}
	
}
