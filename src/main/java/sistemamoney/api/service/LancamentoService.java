package sistemamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistemamoney.api.exception.PessoaInexistenteOuInativaException;
import sistemamoney.api.model.Lancamento;
import sistemamoney.api.model.Pessoa;
import sistemamoney.api.repository.LancamentoRepository;
import sistemamoney.api.repository.PessoaRepository;

/**
 * @author junior
 * GitHub : jotanaval
 * email: jotanaval2009@gmail.com
 */
@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
			if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
				validarPessoa(lancamento);				
			}
			/**
			 * Essa classe BeansUtil faz parte do Spring e serve para copiar as propriedade de um objeto e colocar em outro 
			 */
			BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
			
			return lancamentoRepository.save(lancamentoSalvo);
	}
	/**
	 * Verifica se a pessoa existe no banco de dados e também se ela esta ativa
	 * @param lancamento
	 */
	private void validarPessoa(Lancamento lancamento) {
		Pessoa pessoa = null;
		if (lancamento.getPessoa().getCodigo() != null) {
			pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		}
		if (pessoa == null || pessoa.isInativo()) {
			throw new  PessoaInexistenteOuInativaException();
			
		}
		
	}
	/**
	 * Verifica se o lancamento existe no banco de dados
	 * @param codigo
	 * @return
	 */
	private Lancamento buscarLancamentoExistente(Long codigo) {
		Lancamento lancamentoSalvo = lancamentoRepository.findOne(codigo);
		if (lancamentoSalvo == null) {
			throw new IllegalArgumentException();
			
		}
		
		return lancamentoSalvo;
	}


	/**
	 *   Este método verifica se o lançamento esta sendo relacionado a uma pessoa que não esteja ativa 
	 *   para lançar uma exceção
	 *   para que essa verificação ocorra conforme desejado foi necessário criar um método isInativo() na classe Pessoa
	 * @param lancamento
	 * @return
	 */
	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException(); // Classe criada para lançar as exceções relativas a esse método
		}		     
			     return lancamentoRepository.save(lancamento);
		
	}
	public void remover(Long codigo) {
		this.lancamentoRepository.delete(codigo);
		
	}

	
}
