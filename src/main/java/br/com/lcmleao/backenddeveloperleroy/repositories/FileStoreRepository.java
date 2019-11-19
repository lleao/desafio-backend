package br.com.lcmleao.backenddeveloperleroy.repositories;

import br.com.lcmleao.backenddeveloperleroy.entities.FileStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStoreRepository extends JpaRepository<FileStore, Long> {
}
