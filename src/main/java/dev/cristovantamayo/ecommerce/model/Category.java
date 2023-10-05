package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "category")
public class Category {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private String nome;

    @Column(name = "parent_category_id")
    private Integer parentCategoryId;

    public static Category of (Integer id, String nome, Integer parentCategoryId){
        return new Category(id, nome, parentCategoryId);
    }

}
