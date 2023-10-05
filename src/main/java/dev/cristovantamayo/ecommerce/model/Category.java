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
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "table")
    @TableGenerator(name = "table", table = "hibernate_sequences",
            pkColumnName = "sequence_name", pkColumnValue = "category", valueColumnName = "next_val",
            initialValue = 0, allocationSize = 50)
    private Integer id;

    private String nome;

    @Column(name = "parent_category_id")
    private Integer parentCategoryId;

    public static Category of (Integer id, String nome, Integer parentCategoryId){
        return new Category(id, nome, parentCategoryId);
    }

}
