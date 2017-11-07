package sistemamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory() // pode ser em jdbc 
			.withClient("angular")// nome do cliente
			.secret("@ngul@r0")  //senha
			.scopes("read", "write")// os ecopos da aplicação, aqui eu defino o que o cliente pode fazer 
			.authorizedGrantTypes("password", "refresh_token") //usar o passarword flow
			.accessTokenValiditySeconds(1800)
			.refreshTokenValiditySeconds(3600 * 24)// tempo que o teken vai ficar ativo 1800 segundos = 30min
		.and()
		.withClient("mobile")// nome do cliente
		.secret("m0b1l30")  //senha
		.scopes("read")// os ecopos da aplicação, aqui eu defino o que o cliente pode fazer 
		.authorizedGrantTypes("password", "refresh_token") //usar o passarword flow
		.accessTokenValiditySeconds(1800)
		.refreshTokenValiditySeconds(3600 * 24);// tempo que o teken vai ficar ativo 1800 segundos = 30min
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		    endpoints
		    	.tokenStore(tokenStore())
		    	.accessTokenConverter(accessTokenConverter())
		    	.reuseRefreshTokens(false)
		    	.authenticationManager(authenticationManager);// valida o usuário e senha
	}
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("algaworks");
		return accessTokenConverter;
	}
	
	/**
	 * armazena o token
	 * @return
	 */
	@Bean
	public TokenStore  tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
		
	}
	
}
