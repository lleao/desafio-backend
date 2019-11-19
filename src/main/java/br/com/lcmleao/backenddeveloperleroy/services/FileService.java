package br.com.lcmleao.backenddeveloperleroy.services;

import java.io.InputStream;

public interface FileService {
    StorageService.StorageResult uploadFile(String originalFilename, InputStream inputStream);
}
