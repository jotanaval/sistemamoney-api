package sistemamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



/**
 * @author junior
 * GitHub : jotanaval
 * email: jotanaval2009@gmail.com
 */
@ControllerAdvice // essa anotação serve para que este controle observe toda a aplicação
public class SistemamoneyExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSouce;
	
	/**
	 * as mensagem que a aplicação não conseguir ler irão cair nesse método
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {		
		
		String mensagemUsuario = messageSouce.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Erro> erros = criarListaDeErro(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * vai tratar as axceções de erro 500
	 */
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handlEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String mensagemUsuario = messageSouce.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request); 		
	}
	/**
	 *   Este método trata as exceções de violação de chaves estrangeiras relativas aos relacionamentos
	 *   caso um lançamento for associado a uma pessoa ou uma categoria que não exista ele ira tratar esta mensagem
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object>handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request){
		String mensagemUsuario = messageSouce.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request); 
			
	}
	
	
	/*BindingResult  possui uma lista com todos os erros
	 * bindingResult.getFieldErros() é uma lista de erros que é percorrida pelo for
	 * passa uma lista de erros para os metodos 
	 */
	private List<Erro>criarListaDeErro(BindingResult bindingResult){
		List<Erro>erros = new ArrayList<>();
		
		for (FieldError fieldError : bindingResult.getFieldErrors()) {					
		String mensagemUsuario =messageSouce.getMessage(fieldError, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = fieldError.toString();
		erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));		
		
		}
		return erros;
	}
	
	/**
	 * Classe criada para que possamos passar uma mensagem para o usuario e outra para o desenvolvedor
	 * @author junior
	 *
	 */
	public static class Erro {
		
		private String mensagemUsuario;
		private String mensagemDesenvolvedor;
		
		public Erro(String mensagemUsuario,String mensagemDesenvolvedor ) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}
		
		
	}

}
