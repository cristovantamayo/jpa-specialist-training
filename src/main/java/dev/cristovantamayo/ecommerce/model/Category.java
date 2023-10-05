package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class Category {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private String nome;

    private Integer parentCategoryId;

}
