package br.com.lcmleao.backenddeveloperleroy.services.impl;

import br.com.lcmleao.backenddeveloperleroy.dto.CategoryDTO;
import br.com.lcmleao.backenddeveloperleroy.dto.ItemDTO;
import br.com.lcmleao.backenddeveloperleroy.entities.Category;
import br.com.lcmleao.backenddeveloperleroy.entities.Item;
import br.com.lcmleao.backenddeveloperleroy.repositories.CategoryRepository;
import br.com.lcmleao.backenddeveloperleroy.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> listaAll() {
        return categoryRepository.findAll().stream().map(
                (entity) -> toDTO(entity)
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDTO> listById(Long id) {
        return categoryRepository.findById(id).map((entity) -> toDTO(entity));
    }

    private CategoryDTO toDTO(Category entity) {
        return CategoryDTO.builder()
                .category(entity.getCategory())
                .id(entity.getId())
                .build();
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
