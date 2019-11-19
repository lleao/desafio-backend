package br.com.lcmleao.backenddeveloperleroy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

    @GetMapping
    public ResponseEntity<Object> getAllCategories() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Object> getCategoryById(@PathParam("id") Long id) {
        return ResponseEntity.notFound().build();
    }
}
