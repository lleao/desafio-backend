package br.com.lcmleao.backenddeveloperleroy.controllers;

import br.com.lcmleao.backenddeveloperleroy.dto.ItemDTO;
import br.com.lcmleao.backenddeveloperleroy.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/category/{id}")
    public ResponseEntity<Object> geItensByCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(itemService.listaAll(id));
    }

    @PutMapping("/category/{catId}/item")
    public ResponseEntity<Object> updateItem(
        @PathVariable("catId") Long categoryId,
        @RequestBody ItemDTO item
    ) {
        return ResponseEntity.ok(itemService.atualizar(categoryId, item));
    }

    @DeleteMapping("/category/{catId}/item/{itemId}")
    public ResponseEntity<Object> deleteItem(@PathVariable("catId") Long categoryId, @PathVariable("itemId") Long itemId) {
        itemService.excluir(categoryId, itemId);
        return ResponseEntity.ok().build();
    }
}
