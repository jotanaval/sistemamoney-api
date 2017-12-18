package sistemamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sistemamoney.api.event.RecursoCriadoEvent;
import sistemamoney.api.model.Categoria;
import sistemamoney.api.repository.CategoriaRepository;

/**
 * @author junior
 * GitHub : jotanaval
 * email: jotanaval2009@gmail.com
 */
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')and #oauth2.hasScoped('read')")
	public List<Categoria>listar(){
		return categoriaRepository.findAll();
		
	}
	/**
	 * Pega o codigo da categoria salva através ServletUriComponentsBuilder.fromCurrentRequestUri().path("/codigo")
		 .buildAndExpand(categoriaSalva.getCodigo()).toUri();
	 * @param categoria
	 * @param response
	 */
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA')and #oauth2.hasScoped('write')")
	public ResponseEntity<Categoria> criar(@Valid@RequestBody Categoria categoria, HttpServletResponse response) {
		 Categoria categoriaSalva = categoriaRepository.save(categoria);
		 
		 publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));		
			return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);		
			
	}
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')and #oauth2.hasScoped('read')")
	public Categoria buscarPeloCodigo(@PathVariable Long codigo) {
		return categoriaRepository.findOne(codigo);
	}

}
