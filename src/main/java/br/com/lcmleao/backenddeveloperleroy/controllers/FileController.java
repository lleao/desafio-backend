package br.com.lcmleao.backenddeveloperleroy.controllers;

import br.com.lcmleao.backenddeveloperleroy.exceptions.StorageException;
import br.com.lcmleao.backenddeveloperleroy.services.FileService;
import br.com.lcmleao.backenddeveloperleroy.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;


    @PostMapping(path = "/upload")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file) {

        StorageService.StorageResult result;
        try {
            result = fileService.uploadFile(file.getOriginalFilename(), file.getInputStream() );
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            throw new StorageException(e);
        }
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity handle(StorageException ex) {
        return ResponseEntity.badRequest().build();
    }

}
