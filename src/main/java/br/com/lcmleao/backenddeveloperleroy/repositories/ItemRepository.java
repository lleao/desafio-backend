package br.com.lcmleao.backenddeveloperleroy.repositories;

import br.com.lcmleao.backenddeveloperleroy.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
