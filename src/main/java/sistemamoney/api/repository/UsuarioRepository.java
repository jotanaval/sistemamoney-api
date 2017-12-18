package sistemamoney.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sistemamoney.api.model.Usuario;

/**
 * @author junior
 * GitHub : jotanaval
 * email: jotanaval2009@gmail.com
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	public Optional<Usuario>findByEmail(String email);
}
