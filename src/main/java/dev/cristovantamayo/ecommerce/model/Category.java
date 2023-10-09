package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "category")
public class Category extends EntityBaseInteger {

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
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
