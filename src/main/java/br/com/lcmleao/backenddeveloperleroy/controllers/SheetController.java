package br.com.lcmleao.backenddeveloperleroy.controllers;

import br.com.lcmleao.backenddeveloperleroy.dto.SheetDTO;
import br.com.lcmleao.backenddeveloperleroy.exceptions.SheetException;
import br.com.lcmleao.backenddeveloperleroy.services.SheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "Api para manipulação de planilha. Requer que o arquivo já tenha sido enviado"
)
@RestController
@RequestMapping(path = "/sheet", produces = MediaType.APPLICATION_JSON_VALUE)
public class SheetController {

    @Autowired
    private SheetService sheetService;

    @ApiOperation(value = "Retorna todas as listas cadastradas")
    @GetMapping
    public ResponseEntity<List<SheetDTO>> geAllSheets() {
        return ResponseEntity.ok(sheetService.listaAll());
    }

    @ApiOperation(value = "Retorna uma lista baseado no id")
    @GetMapping("/{id}")
    public ResponseEntity<SheetDTO> getSheetById(@ApiParam(required = true) @PathVariable("id") Long id) {
        return ResponseEntity.ok(
                sheetService.findById(id).orElseThrow( () -> new SheetException("Planilha não localizada", 404) )
        );
    }

    @ApiOperation(value = "Chamada manual para efetuar o processamento de uma lista")
    @PostMapping("/process/{id}")
    public ResponseEntity<Object> processSheetById(@ApiParam(required = true) @PathVariable("id") Long id) {
        sheetService.processSheet(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Retorna o status de processamento de uma lista.\n" +
            "Se retornar 404 ou a lista não existe ou não foi processada ou está em processamento.\n" +
            "Se retornar 200 com true, significa que foi processada com sucesso. Se retornar 200 com false, não foi processada e algum erro ocorreu")
    @GetMapping(path = "/{id}/status")
    public ResponseEntity<Boolean> getStatus(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                sheetService.getStatusById(id)
        );
    }
    @ExceptionHandler(SheetException.class)
    public ResponseEntity handle(SheetException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ex);
    }
}
