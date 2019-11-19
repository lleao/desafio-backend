package br.com.lcmleao.backenddeveloperleroy.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
@ConfigurationProperties(prefix = "storage")
@Data
public class StorageConfiguration {
    private Path directory;
}
