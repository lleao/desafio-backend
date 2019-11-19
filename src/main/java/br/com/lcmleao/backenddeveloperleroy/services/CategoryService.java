package br.com.lcmleao.backenddeveloperleroy.services;

import br.com.lcmleao.backenddeveloperleroy.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public List<CategoryDTO> listaAll();
    public Optional<CategoryDTO> listById(Long id);
}
