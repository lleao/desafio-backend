package br.com.lcmleao.backenddeveloperleroy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Long id;

    private String code;

    private String name;

    private Boolean freeShipping;

    private String description;

    private BigDecimal price;
}
