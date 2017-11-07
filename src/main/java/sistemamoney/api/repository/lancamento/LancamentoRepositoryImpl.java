package sistemamoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import sistemamoney.api.model.Categoria_;
import sistemamoney.api.model.Lancamento;
import sistemamoney.api.model.Lancamento_;
import sistemamoney.api.model.Pessoa;
import sistemamoney.api.model.Pessoa_;
import sistemamoney.api.repository.ResumoLancamento;
import sistemamoney.api.repository.filter.LancamentoFilter;

/**
 * Classe criada para fazer um filtro para consulta
 * 
 * @author junior
 *
 */
public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder(); // basico
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);// basico
		Root<Lancamento> root = criteria.from(Lancamento.class);
		// criar as restições
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Lancamento> query = manager.createQuery(criteria);// basico
		adicionarRestricoesDepaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter)) ;
	}
	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento>root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(ResumoLancamento.class
				, root.get(Lancamento_.codigo), root.get(Lancamento_.descricao)
				, root.get(Lancamento_.dataVencimento), root.get(Lancamento_.dataPagamento)
				, root.get(Lancamento_.valor), root.get(Lancamento_.tipo)
				, root.get(Lancamento_.categoria).get(Categoria_.nome)
				, root.get(Lancamento_.pessoa).get(Pessoa_.nome)));
		
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);// basico
		adicionarRestricoesDepaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter)) ;
	}


	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {

		List<Predicate> predicates = new ArrayList<>();

		if (! StringUtils.isEmpty(lancamentoFilter.getDescricao())) {// classe do org.apache.SpringFramework.Utils
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)),
					"%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		if (lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento),
					lancamentoFilter.getDataVencimentoDe()));
		}
		if (lancamentoFilter.getDatavencimentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento),
					lancamentoFilter.getDatavencimentoAte()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}
	/**
	 *  Adiciona as restrições para a paginação
	 * @param query
	 * @param pageable
	 */
      private void adicionarRestricoesDepaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);// mostra o primeiro registro da pagina
		query.setMaxResults(totalRegistrosPorPagina); // mostra o total de registros por pagina
		
	}
      
      /**
       * Calcula a quantidade de registros 
       * @param lancamentoFilter
       * @return
       */
      private Long total(LancamentoFilter lancamentoFilter) {
    	  CriteriaBuilder builder = manager.getCriteriaBuilder(); // basico
  		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);// basico
  		Root<Lancamento> root = criteria.from(Lancamento.class);
  		// criar as restições
  		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
  		criteria.where(predicates);

  		criteria.select(builder.count(root));
  		return manager.createQuery(criteria).getSingleResult();
  	}


	

}
