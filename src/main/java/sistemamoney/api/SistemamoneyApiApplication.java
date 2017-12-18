package sistemamoney.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import sistemamoney.api.config.property.SistemamoneyApiProperty;
/**
 * @author junior
 * GitHub : jotanaval
 * email: jotanaval2009@gmail.com
 */
@SpringBootApplication
@EnableConfigurationProperties(SistemamoneyApiProperty.class)
public class SistemamoneyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemamoneyApiApplication.class, args);
	}
}
