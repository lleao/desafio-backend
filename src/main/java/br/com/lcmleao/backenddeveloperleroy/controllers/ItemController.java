package br.com.lcmleao.backenddeveloperleroy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(path = "/item")
public class ItemController {

    @GetMapping("/category/{id}")
    public ResponseEntity<Object> geItensByCategory(@PathParam("id") Long id) {
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/category/{id}/item/{id}")
    public ResponseEntity<Object> updateItem(@RequestBody Object item) {
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/category/{id}/item/{id}")
    public ResponseEntity<Object> deleteItem() {
        return ResponseEntity.notFound().build();
    }
}
