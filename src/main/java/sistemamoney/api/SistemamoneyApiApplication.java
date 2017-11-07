package sistemamoney.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import sistemamoney.api.config.property.SistemamoneyApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(SistemamoneyApiProperty.class)
public class SistemamoneyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemamoneyApiApplication.class, args);
	}
}
