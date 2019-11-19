package br.com.lcmleao.backenddeveloperleroy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileStoreDTO {

    private Long id;

    private LocalDateTime uploadedAt;

    private URL resource;
}
