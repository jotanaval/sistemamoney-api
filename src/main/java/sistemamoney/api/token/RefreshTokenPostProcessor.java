package sistemamoney.api.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import sistemamoney.api.config.property.SistemamoneyApiProperty;

/**
 *   Toda requisição ResponseBoby para OAuth2AccessToken será interceptada por esta classe e tratada 
 *   pelos métodos descritos aqui!
 * @author junior
 *
 */

/**
 * @author junior
 * GitHub : jotanaval
 * email: jotanaval2009@gmail.com
 */
@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Autowired
	private SistemamoneyApiProperty sistemamoney;
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		
		return returnType.getMethod().getName().equals("postAccessTpken");
	}
	
	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType,Class<? extends HttpMessageConverter<?>> selectedConverterType, 
			ServerHttpRequest request, ServerHttpResponse response) {
		
		HttpServletRequest req = ((ServletServerHttpRequest)request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse)response).getServletResponse();
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken)body;
		
		String refreshToken = body.getRefreshToken().getValue();
		adicionarRefreshTokenNoCookie(refreshToken, req, resp);
		removerRefreshTokenDoBoby(token);
		
		
		
		return body;
	}

	private void removerRefreshTokenDoBoby(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
		
	}

	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
		
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(sistemamoney.getSeguranca().isEnableHttps());   //TODO: MUDAR PARA TRUE EM PRODUÇÃO
		refreshTokenCookie.setPath(req.getContextPath()+ "/oauth/token");
		refreshTokenCookie.setMaxAge(2592000);
		resp.addCookie(refreshTokenCookie);
		
	}
}
