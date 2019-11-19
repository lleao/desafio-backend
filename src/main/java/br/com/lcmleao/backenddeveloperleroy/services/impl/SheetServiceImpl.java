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

/***
 * Implementação do serviço de Sheet
 */
@Service
public class SheetServiceImpl implements SheetService {
    @Autowired
    private SheetProcessor sheetProcessor;

    @Autowired
    private SheetRepository sheetRepository;

    /***
     * Retorna uma lista de DTO contendo todas as Sheet
     * @return List<SheetDTO>
     */
    @Override
    public List<SheetDTO> listaAll() {
        return sheetRepository.findAll().stream().map(
                (entity) -> toDTO(entity)
        ).collect(Collectors.toList());
    }
    /***
     * Retorna um opcional do DTO contendo o item baseado no id
     * @return Optional<SheetDTO>
     */
    @Override
    public Optional<SheetDTO> listById(Long id) {
        return sheetRepository.findById(id).map((entity) -> toDTO(entity));
    }
    /***
     * Informa o status de processamento da planilha
     * @return Retorna true caso o status de processamento da planilha seja sucesso ou falso
     *      * caso não tenha sido importada ou tenha ocorrido erro na importação
     */
    @Override
    public Boolean getStatusById(Long id) {
        return sheetRepository.findById(id).map(
                (entity) -> entity.getSuccess()
        ).orElse(false);
    }

    /***
     * Solicita o processamento sincrono da planilha
     * @param id Id da planilha
     */
    @Override
    public void processSheet(Long id) {
        sheetProcessor.processSheet(id);
    }

    /***
     * Metódo auxiliar para transformar a entidade em DTO
     * @param entity
     * @return SheetDTO
     */
    private SheetDTO toDTO(Sheet entity) {
        return SheetDTO.builder()
                .id( entity.getId() )
                .state( entity.getState() )
                .success( entity.getSuccess() )
                .build();
    }

}
