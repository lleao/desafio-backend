package br.com.lcmleao.backenddeveloperleroy.services.impl;

import br.com.lcmleao.backenddeveloperleroy.entities.FileStore;
import br.com.lcmleao.backenddeveloperleroy.exceptions.StorageException;
import br.com.lcmleao.backenddeveloperleroy.repositories.FileStoreRepository;
import br.com.lcmleao.backenddeveloperleroy.services.FileService;
import br.com.lcmleao.backenddeveloperleroy.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileStoreRepository fileStoreRepository;

    @Autowired
    private StorageService storageService;

    public void saveFile() {

    }

    @Override
    public StorageService.StorageResult uploadFile(String originalFilename, InputStream inputStream) {
        StorageService.StorageResult result;
        result = storageService.store(originalFilename, new InputStreamResource(inputStream));
        FileStore file = null;
        FileStore saved;
        try {
            file = FileStore.builder()
                    .resource( result.resource().getURL() )
                    .build();
            saved = fileStoreRepository.save(file);
        } catch (IOException e) {
            throw new StorageException(e);
        }

        return result;
    }
}
