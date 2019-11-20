package br.com.lcmleao.backenddeveloperleroy.dto;

import br.com.lcmleao.backenddeveloperleroy.enums.ProcessState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SheetDTO implements Serializable {
    private Long id;
    private FileStoreDTO fileStore;

    private Boolean success;

    private ProcessState state;
}
