package br.com.lcmleao.backenddeveloperleroy.services.impl;

import br.com.lcmleao.backenddeveloperleroy.dto.FileStoreDTO;
import br.com.lcmleao.backenddeveloperleroy.entities.FileStore;
import br.com.lcmleao.backenddeveloperleroy.exceptions.StorageException;
import br.com.lcmleao.backenddeveloperleroy.repositories.FileStoreRepository;
import br.com.lcmleao.backenddeveloperleroy.services.FileService;
import br.com.lcmleao.backenddeveloperleroy.services.SheetProcessor;
import br.com.lcmleao.backenddeveloperleroy.services.StorageService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/***
 * Implementação do serviço de armazenamento de arquivo
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileStoreRepository fileStoreRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private SheetProcessor sheetProcessor;

    /***
     * Salva o arquivo de forma acessível ao sistema.
     * Move para o local acessível e insere no banco dados referente ao arquivo.
     * @param originalFilename Nome original do arquivo
     * @param inputStream Stream para conteúdo do arquivo
     * @return FileStoreDTO
     */
    @Override
    public FileStoreDTO uploadFile(String originalFilename, InputStream inputStream) {
        StorageService.StorageResult result;
        FileStore file = null;
        FileStore saved;
        String md5Hex;

        result = storageService.store(originalFilename, new InputStreamResource(inputStream));
        try {
            md5Hex = DigestUtils.md5Hex(result.resource().getInputStream()).toUpperCase();
            file = FileStore.builder()
                    .md5(md5Hex)
                    .resource( result.resource().getURL() )
                    .build();
            saved = fileStoreRepository.save(file);
            // Insere o arquivo na fila de processamento
            sheetProcessor.queueFile(saved.getId());
            return FileStoreDTO.builder()
                    .id( saved.getId() )
                    .resource( saved.getResource().getFile() )
                    .uploadedAt( saved.getUploadedAt() )
                    .md5( saved.getMd5() )
                    .build();
        } catch (IOException e) {
            throw new StorageException(e);
        }

    }
}
