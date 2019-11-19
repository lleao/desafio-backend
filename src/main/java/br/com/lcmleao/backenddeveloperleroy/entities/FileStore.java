package br.com.lcmleao.backenddeveloperleroy.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.net.URL;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_file_store")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uploaded_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime uploadedAt;

    @Column(name = "resource", nullable = false)
    private URL resource;
}
