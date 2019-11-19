package br.com.lcmleao.backenddeveloperleroy.services;

import br.com.lcmleao.backenddeveloperleroy.dto.FileStoreDTO;

import java.io.InputStream;

public interface FileService {
    FileStoreDTO uploadFile(String originalFilename, InputStream inputStream);
}
