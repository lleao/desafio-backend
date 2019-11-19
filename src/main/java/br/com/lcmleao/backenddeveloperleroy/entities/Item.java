package br.com.lcmleao.backenddeveloperleroy.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lm")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "free_shipping")
    private Boolean freeShipping;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @JoinColumn(name = "fk_category")
    @ManyToOne
    private Category category;
}
