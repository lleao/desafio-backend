package br.com.lcmleao.backenddeveloperleroy.services;

import br.com.lcmleao.backenddeveloperleroy.dto.SheetDTO;

import java.util.List;
import java.util.Optional;

public interface SheetService {
    public List<SheetDTO> listaAll();
    public Optional<SheetDTO> findById(Long id );
    public Boolean getStatusById( Long id );
    public void processSheet( Long id );
}
