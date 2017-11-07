package sistemamoney.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistemamoney.api.exception.PessoaInexistenteOuInativaException;
import sistemamoney.api.model.Lancamento;
import sistemamoney.api.model.Pessoa;
import sistemamoney.api.repository.LancamentoRepository;
import sistemamoney.api.repository.PessoaRepository;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	
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
