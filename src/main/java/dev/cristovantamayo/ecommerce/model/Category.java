package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "category_primary_key_sequence")
    private Integer id;

    private String nome;

    @Column(name = "parent_category_id")
    private Integer parentCategoryId;

    public static Category of (Integer id, String nome, Integer parentCategoryId){
        return new Category(id, nome, parentCategoryId);
    }

}
