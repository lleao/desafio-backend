package br.com.lcmleao.backenddeveloperleroy.services;

import org.springframework.core.io.Resource;

public interface StorageService {
    public interface StorageResult {
        Resource resource();
    }
    public StorageResult store(String originalFilename, Resource origin);
}
