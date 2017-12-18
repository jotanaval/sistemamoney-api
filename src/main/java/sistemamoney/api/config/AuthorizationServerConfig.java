package sistemamoney.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import sistemamoney.api.config.token.CustomTokenEnhancer;
@Profile("oauth-security")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private AuthenticationManager authenticationManager;
//so para subir no git 
	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer)
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory() // pode ser em jdbc 
			.withClient("angular")// nome do cliente
			.secret("@ngul@r0")  //senha
			.scopes("read", "write")// os ecopos da aplicação, aqui eu defino o que o cliente pode fazer 
			.authorizedGrantTypes("password", "refresh_token") //usar o passarword flow
			.accessTokenValiditySeconds(1800)
			.refreshTokenValiditySeconds(3600 * 24)
			.and()
			.withClient("mobile")
			.secret("m0b1l30")
			.scopes("read")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(1800)
            .refreshTokenValiditySeconds(3600 * 24);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnchancher(), accessTokenConverter()));
		    endpoints
		    	.tokenStore(tokenStore())
		    	.tokenEnhancer(tokenEnhancerChain)
		    	.reuseRefreshTokens(false)
		    	.authenticationManager(authenticationManager);// valida o usuário e senha
	}
	
	@Bean
	public TokenEnhancer tokenEnchancher() {
			return new CustomTokenEnhancer();
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
