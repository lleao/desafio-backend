package br.com.lcmleao.backenddeveloperleroy.repositories;

import br.com.lcmleao.backenddeveloperleroy.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
