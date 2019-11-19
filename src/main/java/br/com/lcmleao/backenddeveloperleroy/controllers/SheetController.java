package br.com.lcmleao.backenddeveloperleroy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(path = "/sheet")
public class SheetController {

    @GetMapping
    public ResponseEntity<Object> geAllSheets() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> geSheetById(@PathParam("id") Long id) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/{id}/status")
    public ResponseEntity<Object> getStatus(@PathParam("id") Long id) {
        return ResponseEntity.notFound().build();
    }

}
