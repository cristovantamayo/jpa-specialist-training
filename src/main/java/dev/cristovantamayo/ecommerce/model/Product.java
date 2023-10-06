package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "product")
public class Product {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;
    
    public BigDecimal price;

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @Column(name = "categories")
    private List<Category> categories;

    @OneToOne(mappedBy = "product")
    private Stock stock;

    public static Product of (Integer id, String name, String description, BigDecimal price,
                              List<Category> categories, Stock stock) {
        return new Product(id, name, description, price, categories, stock);
    }
}
