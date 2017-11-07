package sistemamoney.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import sistemamoney.api.event.RecursoCriadoEvent;
import sistemamoney.api.exception.PessoaInexistenteOuInativaException;
import sistemamoney.api.exceptionhandler.SistemamoneyExceptionHandler.Erro;
import sistemamoney.api.model.Lancamento;
import sistemamoney.api.repository.LancamentoRepository;
import sistemamoney.api.repository.ResumoLancamento;
import sistemamoney.api.repository.filter.LancamentoFilter;
import sistemamoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService  lancamentoService;
	
	@Autowired
	private MessageSource messageSouce;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	/**
	 * Retorna todos os lançamentos cadastrados no banco de dados
	 * @return
	 */
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')and #oauth2.hasScoped('read')")
	public Page<Lancamento>pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}
	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')and #oauth2.hasScoped('read')")
	public Page<ResumoLancamento>resumir(LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoRepository.resumir(lancamentoFilter, pageable);
	}
	/**
	 * Retorna Lançamento pelo código
	 * @param codigo
	 * @return
	 */
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')and #oauth2.hasScoped('read')")
	public Lancamento buscarPorCodigo(@PathVariable Long  codigo) {
		return lancamentoRepository.findOne(codigo);
	}
	
	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA')and #oauth2.hasScoped('write')")
	public void remover(@PathVariable Long codigo) {
		this.lancamentoService.remover(codigo);
		
	}
	
	/**
	 * Recebe um lançamento e registra no banco de dados respeitando a regra de negocio implementada no LancamentoService.
	 * Caso aconteça algum problema chama o método pessoaInexistenteOuInativaException, desta classe,  que tratará a exceção
	 * @param lancamento
	 * @param response
	 * @return
	 */
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA')and #oauth2.hasScoped('write')")
	public ResponseEntity<Lancamento>criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response ){
		
		Lancamento lancamentosalvo = this.lancamentoService.salvar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentosalvo.getCodigo()));		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentosalvo);
		
	}
	
	/**
	 * Este método recebe uma exceção do tipo persistenceException e retorna uma mensagem para o usuario e o desenvolvedor
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ PessoaInexistenteOuInativaException.class})
	public ResponseEntity<Object>pessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex, WebRequest request){
		//Lança uma mensagem para o Usuário que esta registrada no messages.properties através do parametro pessoa.inexistente-ou-inativa
		String mensagemUsuario = messageSouce.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		//Lança uma mensagem para o desenvolvedor
		String mensagemDesenvolvedor =  ex.toString();
		//Exibe a lista de erros
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
			
	}
	
}























