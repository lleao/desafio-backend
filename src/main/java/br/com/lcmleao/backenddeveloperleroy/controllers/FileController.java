package br.com.lcmleao.backenddeveloperleroy.controllers;

import br.com.lcmleao.backenddeveloperleroy.dto.FileStoreDTO;
import br.com.lcmleao.backenddeveloperleroy.exceptions.StorageException;
import br.com.lcmleao.backenddeveloperleroy.services.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "Api para upload de arquivo"
)
@RestController
@RequestMapping(value = "/file", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(
            value = "Recebe um MultipartFile e o salva em diretório temporário",
            response = FileStoreDTO.class
    )
    @PostMapping(path = "/upload")
    public ResponseEntity<FileStoreDTO> upload(@ApiParam(required = true) @RequestParam("file") MultipartFile file) {

        FileStoreDTO result;
        try {
            result = fileService.uploadFile(file.getOriginalFilename(), file.getInputStream() );
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            throw new StorageException(e);
        }
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity handle(StorageException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
