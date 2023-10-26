package dev.cristovantamayo.ecommerce.model;

import dev.cristovantamayo.ecommerce.model.dto.CategoryDTO;
import dev.cristovantamayo.ecommerce.model.dto.ProductDTO;
import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "category",
        uniqueConstraints = { @UniqueConstraint(name = "unq_category_name", columnNames = { "name" }) },
        indexes = { @Index(name = "idx_category_name", columnList = "name")})
public class Category extends EntityBaseInteger {

    @Column(length = 100, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_category_id",
            foreignKey = @ForeignKey(name = "fk_category_parent_category"))
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<Category> categories;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products;

    public static Category of (String nome, Category parentCategory,
                               List<Category> parentCategories, List<Product> products){

        return new Category(nome, parentCategory, parentCategories, products);
    }

}
