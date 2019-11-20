package br.com.lcmleao.backenddeveloperleroy.controllers;

import br.com.lcmleao.backenddeveloperleroy.dto.SheetDTO;
import br.com.lcmleao.backenddeveloperleroy.services.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/sheet")
public class SheetController {

    @Autowired
    private SheetService sheetService;

    @GetMapping
    public ResponseEntity<List<SheetDTO>> geAllSheets() {
        return ResponseEntity.ok(sheetService.listaAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSheetById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(sheetService.listById(id));
    }

    @PostMapping("/process/{id}")
    public ResponseEntity<Object> processSheetById(@PathVariable("id") Long id) {
        sheetService.processSheet(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{id}/status")
    public ResponseEntity<Object> getStatus(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                sheetService.getStatusById(id)
        );
    }

}
