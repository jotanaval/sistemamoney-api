package sistemamoney.api.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author junior
 * GitHub : jotanaval
 * email: jotanaval2009@gmail.com
 */
public class GeradorSenha {

	public static void main(String[]args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
	}
}
