package sistemamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sistemamoney.api.model.Lancamento;
import sistemamoney.api.repository.lancamento.LancamentoRepositoryQuery;

/**
 * @author junior
 * GitHub : jotanaval
 * email: jotanaval2009@gmail.com
 */
public interface LancamentoRepository  extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{
	
	

}
