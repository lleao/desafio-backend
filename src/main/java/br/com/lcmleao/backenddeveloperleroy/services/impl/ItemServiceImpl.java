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

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<ItemDTO> listaAll(Long categoryId) {
        return itemRepository.findAllItemByCategoryId(categoryId).stream().map(
                (entity) -> toDTO(entity)
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<ItemDTO> listById(Long id) {
        return itemRepository.findById(id).map( (each) -> toDTO(each) );
    }

    @Override
    public ItemDTO atualizar(Long categoryId, ItemDTO item) {
        Item itemEntity = itemRepository.findById(item.getId()).orElseThrow(() -> new ItemException("Item inesistente"));
        itemEntity = merge(itemEntity, item);
        itemEntity = itemRepository.save(itemEntity);
        return toDTO(itemEntity);
    }

    @Override
    public void excluir(Long categoryId, Long itemId) {
        Item itemEntity = itemRepository.findById(itemId).orElseThrow(() -> new ItemException("Item inesistente"));
        itemRepository.delete(itemEntity);
    }

    private Item merge(Item itemEntity, ItemDTO item) {
        itemEntity.setCode( merge( itemEntity.getCode(), item.getCode() ) );
        itemEntity.setDescription( merge( itemEntity.getDescription(), item.getDescription() ) );
        itemEntity.setFreeShipping( merge( itemEntity.getFreeShipping(), item.getFreeShipping() ) );
        itemEntity.setName( merge( itemEntity.getName(), item.getName() ) );
        itemEntity.setPrice( merge( itemEntity.getPrice(), item.getPrice() ) );
        return itemEntity;
    }

    private <T> T merge( T a, T b ) {
        if ( a.equals(b) ) {
            return a;
        }
        return b;
    }


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
