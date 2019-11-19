package br.com.lcmleao.backenddeveloperleroy.services;

import br.com.lcmleao.backenddeveloperleroy.dto.ItemDTO;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    public List<ItemDTO> listaAll(Long categoryId);
    public Optional<ItemDTO> listById(Long id);

    ItemDTO atualizar(Long categoryId, ItemDTO item);

    void excluir(Long categoryId, Long itemId);

}
