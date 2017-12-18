package sistemamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author junior
 * GitHub : jotanaval
 * email: jotanaval2009@gmail.com
 */
@ConfigurationProperties("sistemamoney")
public class SistemamoneyApiProperty {

	private String origemPermitida = "http://localhost:8000";

	private final Seguranca seguranca = new Seguranca();

	public static class Seguranca {

		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}

	}

	public Seguranca getSeguranca() {
		return seguranca;
	}

	public String getOrigemPermitida() {
		return origemPermitida;
	}

	public void setOrigemPermitida(String origemPermitida) {
		this.origemPermitida = origemPermitida;
	}

}
