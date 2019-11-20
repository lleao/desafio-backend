package br.com.lcmleao.backenddeveloperleroy.controllers;

import br.com.lcmleao.backenddeveloperleroy.dto.ItemDTO;
import br.com.lcmleao.backenddeveloperleroy.exceptions.ItemException;
import br.com.lcmleao.backenddeveloperleroy.services.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "Api para manipulação de Item"
)
@RestController
@RequestMapping(path = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(
            value = "Baseado no id da categoria, retorna uma lista de itens"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<Object> geItensByCategory(@ApiParam(required = true, name = "id") @PathVariable("id") Long id) {
        return ResponseEntity.ok(itemService.listaAll(id));
    }

    @ApiOperation(
            value = "Baseado no id da categoria e no item, efetua a atualização do item"
    )
    @PutMapping("/category/{catId}/item")
    public ResponseEntity<Object> updateItem(
        @ApiParam(required = true) @PathVariable("catId") Long categoryId,
        @ApiParam(required = true) @RequestBody ItemDTO item
    ) {
        return ResponseEntity.ok(itemService.atualizar(categoryId, item));
    }
    @ApiOperation(
            value = "Baseado no id da categoria e no id do item, exclui o item"
    )
    @DeleteMapping("/category/{catId}/item/{itemId}")
    public ResponseEntity<Object> deleteItem(
            @ApiParam(required = true) @PathVariable("catId") Long categoryId,
            @ApiParam(required = true) @PathVariable("itemId") Long itemId) {
        itemService.excluir(categoryId, itemId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ItemException.class)
    public ResponseEntity handle(ItemException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
