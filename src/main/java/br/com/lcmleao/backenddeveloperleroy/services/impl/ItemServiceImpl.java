package br.com.lcmleao.backenddeveloperleroy.services.impl;

import br.com.lcmleao.backenddeveloperleroy.dto.ItemDTO;
import br.com.lcmleao.backenddeveloperleroy.entities.Item;
import br.com.lcmleao.backenddeveloperleroy.exceptions.ItemException;
import br.com.lcmleao.backenddeveloperleroy.repositories.CategoryRepository;
import br.com.lcmleao.backenddeveloperleroy.repositories.ItemRepository;
import br.com.lcmleao.backenddeveloperleroy.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/***
 * Implementação do serviço de Item
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    /***
     * Retorna uma lista de DTO contendo todos os itens de uma Category
     * @return List<ItemDTO>
     */
    @Override
    public List<ItemDTO> listaAll(Long categoryId) {
        return itemRepository.findAllItemByCategoryId(categoryId).stream().map(
                (entity) -> toDTO(entity)
        ).collect(Collectors.toList());
    }

    /***
     * Retorna um opcional do DTO contendo o item baseado no id
     * @return Optional<ItemDTO>
     */
    @Override
    public Optional<ItemDTO> listById(Long id) {
        return itemRepository.findById(id).map( (each) -> toDTO(each) );
    }

    /***
     * Atualiza um item baseado em Category e no id do item
     * @param categoryId
     * @param item DTO do item. Deve ter o id preenchido.
     * @return ItemDTO atualizado
     */
    @Override
    public ItemDTO atualizar(Long categoryId, ItemDTO item) {
        Item itemEntity = itemRepository.findById(item.getId()).orElseThrow(() -> new ItemException("Item inesistente"));
        itemEntity = merge(itemEntity, item);
        itemEntity = itemRepository.save(itemEntity);
        return toDTO(itemEntity);
    }

    /***
     * Excluí um item baseado em Category e no id do item
     * @param categoryId
     * @param itemId
     */
    @Override
    public void excluir(Long categoryId, Long itemId) {
        Item itemEntity = itemRepository.findById(itemId).orElseThrow(() -> new ItemException("Item inesistente"));
        itemRepository.delete(itemEntity);
    }

    /***
     * Metódo auxiliar que exetua o merge entre a entidade e o DTO
     * @param itemEntity entidade
     * @param item dto
     * @return Entidade após merge
     */
    private Item merge(Item itemEntity, ItemDTO item) {
        itemEntity.setCode( merge( itemEntity.getCode(), item.getCode() ) );
        itemEntity.setDescription( merge( itemEntity.getDescription(), item.getDescription() ) );
        itemEntity.setFreeShipping( merge( itemEntity.getFreeShipping(), item.getFreeShipping() ) );
        itemEntity.setName( merge( itemEntity.getName(), item.getName() ) );
        itemEntity.setPrice( merge( itemEntity.getPrice(), item.getPrice() ) );
        return itemEntity;
    }

    /***
     * Metódo auxiliar que efetua o merge de objetos
     * @param a
     * @param b
     * @return Se a == b então a senão b
     */
    private <T> T merge( T a, T b ) {
        if ( a.equals(b) ) {
            return a;
        }
        return b;
    }

    /***
     * Metódo auxiliar que transforma a entidade Item em ItemDTO
     * @param eachItem entidade
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
