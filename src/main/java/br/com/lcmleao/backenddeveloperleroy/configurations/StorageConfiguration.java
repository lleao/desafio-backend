package br.com.lcmleao.backenddeveloperleroy.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@ConfigurationProperties(prefix = "storage")
@Data
public class StorageConfiguration {
    private Resource directory;
}
