package sistemamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sistemamoney.api.model.Pessoa;
import sistemamoney.api.repository.pessoa.PessoaRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery{

	

}
