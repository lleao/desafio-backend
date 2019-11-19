package br.com.lcmleao.backenddeveloperleroy.services.impl;

import br.com.lcmleao.backenddeveloperleroy.configurations.StorageConfiguration;
import br.com.lcmleao.backenddeveloperleroy.exceptions.StorageException;
import br.com.lcmleao.backenddeveloperleroy.exceptions.StorageInitException;
import br.com.lcmleao.backenddeveloperleroy.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Implementação do armazenamento de arquivos
 * */
@Service
public class StorageServiceImpl implements StorageService {

    /**
     * Configurações relativas ao armazenamento
     **/
    @Autowired
    private StorageConfiguration storageConfiguration;

    /**
     * Salva o arquivo no local informado na configuração
     *
     * @param originalFilename
     * @param origin Arquivo de origem.
     * @return StorageResult Resultado da operação.
     * @throws StorageException Lançada em caso de execução inadequada
     * */
    @Override
    public StorageResult store(String originalFilename, Resource origin) {
        init();
        Path target;
        Resource dir = storageConfiguration.getDirectory();
        try {
            target = Paths.get(dir.getURI());
            Path result = getUniqueName(target, originalFilename);
            Files.copy( origin.getInputStream(), result, StandardCopyOption.REPLACE_EXISTING);
            return () -> new FileSystemResource(result);

        } catch (IOException e) {
            throw new StorageException(e);
        }
    }
    /**
     * Cria a estrutura de pastas caso não existam
     * */
    private void init() {
        try {
            Files.createDirectories(storageConfiguration.getDirectory().getFile().toPath());
        } catch (IOException e) {
            throw new StorageInitException("Não foi possível criar o local para armazenamento: " + storageConfiguration.getDirectory().getFilename(), e);
        }
    }

    /***
     * Baseado na hierarquia de arquivos, cria um nome único para o arquivo.
     * @param target Diretório alvo
     * @param name Nome do arquivo
     * @return Novo e único path
     */
    private Path getUniqueName(Path target, String name) {
        Path newPath;
        String sufixo = "";
        int ctd = 0;
        String filename = StringUtils.stripFilenameExtension(name);
        String ext = StringUtils.getFilenameExtension(name);
        if ( null == filename ) {
            filename = target.getFileName().toString();
        }
        if ( null == ext ){
            ext = "";
        }
        do {
            newPath = target.resolve(filename + sufixo + ext);
            sufixo = "_" + ctd++;
        }while(newPath.toFile().exists());

        return newPath;
    }
}
