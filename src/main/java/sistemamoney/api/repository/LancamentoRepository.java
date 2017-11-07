package sistemamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sistemamoney.api.model.Lancamento;
import sistemamoney.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository  extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{
	
	

}
