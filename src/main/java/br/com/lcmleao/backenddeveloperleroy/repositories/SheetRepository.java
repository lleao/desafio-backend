package br.com.lcmleao.backenddeveloperleroy.repositories;

import br.com.lcmleao.backenddeveloperleroy.entities.Sheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheetRepository extends JpaRepository<Sheet, Long> {
}
