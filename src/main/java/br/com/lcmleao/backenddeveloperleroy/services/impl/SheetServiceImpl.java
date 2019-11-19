package br.com.lcmleao.backenddeveloperleroy.services.impl;

import br.com.lcmleao.backenddeveloperleroy.dto.SheetDTO;
import br.com.lcmleao.backenddeveloperleroy.entities.Sheet;
import br.com.lcmleao.backenddeveloperleroy.repositories.SheetRepository;
import br.com.lcmleao.backenddeveloperleroy.services.SheetProcessor;
import br.com.lcmleao.backenddeveloperleroy.services.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SheetServiceImpl implements SheetService {
    @Autowired
    private SheetProcessor sheetProcessor;

    @Autowired
    private SheetRepository sheetRepository;

    @Override
    public List<SheetDTO> listaAll() {
        return sheetRepository.findAll().stream().map(
                (entity) -> toDTO(entity)
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<SheetDTO> listById(Long id) {
        return sheetRepository.findById(id).map((entity) -> toDTO(entity));
    }

    @Override
    public Boolean getStatusById(Long id) {
        return sheetRepository.findById(id).map(
                (entity) -> entity.getSuccess()
        ).orElse(false);
    }

    @Override
    public void processSheet(Long id) {
        sheetProcessor.processSheet(id);
    }


    private SheetDTO toDTO(Sheet enitty) {
        return SheetDTO.builder()
                .id( enitty.getId() )
                .state( enitty.getState() )
                .success( enitty.getSuccess() )
                .build();
    }

}
