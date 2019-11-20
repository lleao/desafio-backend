package br.com.lcmleao.backenddeveloperleroy.configurations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class QueueConfiguration {

    @Value("${spring.cloud.stream.bindings.output.destination}")
    private String queue;
}
