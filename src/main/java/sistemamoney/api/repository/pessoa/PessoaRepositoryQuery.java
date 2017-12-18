package sistemamoney.api.repository.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sistemamoney.api.model.Pessoa;
import sistemamoney.api.repository.filter.PessoaFilter;

/**
 * A classe deve possuir esse nome,PessoaRepositoryQuery, para que o spring data reconheça
 * @author junior
 *
 */

public interface PessoaRepositoryQuery {
	
	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);

}
