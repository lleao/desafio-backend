package br.com.lcmleao.backenddeveloperleroy.controllers;

import br.com.lcmleao.backenddeveloperleroy.dto.CategoryDTO;
import br.com.lcmleao.backenddeveloperleroy.services.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(
    produces = MediaType.APPLICATION_JSON_VALUE,
    value = "Api para consultar categoria"
)
@RestController
@RequestMapping(path = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(
            value = "Retorna uma lista com todas as categorias cadastradas"
    )
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.listaAll());
    }
    @ApiOperation(
            value = "Retorna uma categoria baseado no seu id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@ApiParam(required = true, name = "id")  @PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
}
