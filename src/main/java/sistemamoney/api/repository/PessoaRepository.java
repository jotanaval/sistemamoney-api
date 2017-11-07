package sistemamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sistemamoney.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
