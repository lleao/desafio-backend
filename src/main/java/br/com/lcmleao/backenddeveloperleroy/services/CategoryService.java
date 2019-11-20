package br.com.lcmleao.backenddeveloperleroy.services;

import br.com.lcmleao.backenddeveloperleroy.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    public List<CategoryDTO> listaAll();
    public CategoryDTO findById(Long id);
}
