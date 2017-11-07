package sistemamoney.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sistemamoney.api.model.Lancamento;
import sistemamoney.api.repository.ResumoLancamento;
import sistemamoney.api.repository.filter.LancamentoFilter;

/**
 * A classe deve possuir esse nome,LancamentoRepositoryQuery, para que o spring data reconheça
 * @author junior
 *
 */

public interface LancamentoRepositoryQuery {
		
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<ResumoLancamento>resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

}
