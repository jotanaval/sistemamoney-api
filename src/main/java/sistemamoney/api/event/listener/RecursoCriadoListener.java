package sistemamoney.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import sistemamoney.api.event.RecursoCriadoEvent;

/**
 * @author junior
 * GitHub : jotanaval
 * email: jotanaval2009@gmail.com
 */
@Component
public class RecursoCriadoListener  implements ApplicationListener<RecursoCriadoEvent>{

	
	@Override
	public void onApplicationEvent(RecursoCriadoEvent recursoCriandoEvent) {
		
		HttpServletResponse response = recursoCriandoEvent.getResponse();
		Long codigo = recursoCriandoEvent.getCodigo();
		
		//traz o código da entidade salva no banco de dados
				adicionarHeaderLocation(response, codigo);
		
	}

	private void adicionarHeaderLocation(HttpServletResponse response, Long codigo) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
		 .buildAndExpand(codigo).toUri();  // pega através da requisiao atual o id
		response.setHeader("Location", uri.toASCIIString());
	}

}
