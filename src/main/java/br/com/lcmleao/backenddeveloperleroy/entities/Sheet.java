package br.com.lcmleao.backenddeveloperleroy.entities;

import br.com.lcmleao.backenddeveloperleroy.enums.ProcessState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_file_store", nullable = false)
    private FileStore fileStore;

    @Column(name = "process_success")
    private Boolean success;

    @Column(name = "state")
    @Enumerated(value = EnumType.ORDINAL)
    private ProcessState state;
}
