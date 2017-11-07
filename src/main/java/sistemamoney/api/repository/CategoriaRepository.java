package sistemamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sistemamoney.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
