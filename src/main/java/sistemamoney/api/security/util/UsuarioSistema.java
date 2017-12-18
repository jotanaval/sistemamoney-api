package sistemamoney.api.security.util;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import sistemamoney.api.model.Usuario;

/**
 * @author junior
 * GitHub : jotanaval
 * email: jotanaval2009@gmail.com
 */
public class UsuarioSistema  extends User{
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;


	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}


	public Usuario getUsuario() {
		return usuario;
	}

  
}
