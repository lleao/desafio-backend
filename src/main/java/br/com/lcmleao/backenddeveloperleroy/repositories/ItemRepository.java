package br.com.lcmleao.backenddeveloperleroy.repositories;

import br.com.lcmleao.backenddeveloperleroy.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i\n" +
           "    INNER JOIN FETCH i.category cat\n" +
           "WHERE cat.id = :catId")
    public List<Item> findAllItemByCategoryId(@Param("catId") Long categoryId);
}
