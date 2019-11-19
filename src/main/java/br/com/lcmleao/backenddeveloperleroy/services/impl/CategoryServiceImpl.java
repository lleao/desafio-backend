package br.com.lcmleao.backenddeveloperleroy.services.impl;

import br.com.lcmleao.backenddeveloperleroy.dto.CategoryDTO;
import br.com.lcmleao.backenddeveloperleroy.dto.ItemDTO;
import br.com.lcmleao.backenddeveloperleroy.entities.Category;
import br.com.lcmleao.backenddeveloperleroy.entities.Item;
import br.com.lcmleao.backenddeveloperleroy.repositories.CategoryRepository;
import br.com.lcmleao.backenddeveloperleroy.repositories.ItemRepository;
import br.com.lcmleao.backenddeveloperleroy.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/***
 * Implementação do serviço para acesso a Categoy
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    /***
     * Retorna uma lista de DTO contendo todas as categorias
     * @return List<CategoryDTO>
     */
    @Override
    public List<CategoryDTO> listaAll() {
        return categoryRepository.findAll().stream().map(
                (entity) -> toDTO(entity)
        ).collect(Collectors.toList());
    }
    /***
     * Retorna um opcional do DTO contendo a categoria
     * @return Optional<CategoryDTO>
     */
    @Override
    public Optional<CategoryDTO> listById(Long id) {
        return categoryRepository.findById(id).map((entity) -> toDTO(entity));
    }

    /***
     * Metódo auxiliar para converter a entidade em DTO
     * @param entity
     * @return CategoryDTO
     */
    private CategoryDTO toDTO(Category entity) {
        return CategoryDTO.builder()
                .category(entity.getCategory())
                .id(entity.getId())
                .itens(
                        itemRepository.findAllItemByCategoryId(entity.getId())
                                .stream()
                                .map( (item) -> toDTO(item) )
                                .collect(Collectors.toList())
                )
                .build();
    }
    /***
     * Metódo auxiliar para converter a entidade em DTO
     * @param eachItem
     * @return ItemDTO
     */
    private ItemDTO toDTO(Item eachItem) {
        return ItemDTO.builder()
                .id(eachItem.getId())
                .code( eachItem.getCode() )
                .description( eachItem.getDescription() )
                .freeShipping( eachItem.getFreeShipping() )
                .name( eachItem.getName() )
                .price( eachItem.getPrice() )
                .build();
    }


}
