package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class Product {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private String name;

    private String description;
    
    public BigDecimal price;
}
