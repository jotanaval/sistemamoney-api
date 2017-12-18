package sistemamoney.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import sistemamoney.api.config.property.SistemamoneyApiProperty;

/**
 * @author junior
 * GitHub : jotanaval
 * email: jotanaval2009@gmail.com
 */
@Component
@Order
public class CorsFilter implements Filter{
	
	@Autowired
	private SistemamoneyApiProperty sistemamoneyApiProperty;


	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
	
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		response.setHeader("Access-Control-Allow-Origin",  sistemamoneyApiProperty.getOrigemPermitida());
		response.setHeader("Access-Control-Allow-Credentials","true");
		
		if("OPTIONS".equals(request.getMethod())&& sistemamoneyApiProperty.getOrigemPermitida().equals(request.getHeader("Origin"))) {
			response.setHeader("Access-Control-Allow-Methods",  "POST,GET,DELETE,PUT,OPTIONS");
			response.setHeader("Access-Control-Allow-Headers","Authorization, Content-Type, Accept");
			response.setHeader("Access-Control-Max-Age", "3600");
			
			response.setStatus(HttpServletResponse.SC_OK);
		}else {
			chain.doFilter(req, resp);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
				
	}
	@Override
	public void destroy() {
		
	}

}
