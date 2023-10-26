package dev.cristovantamayo.ecommerce.model;

import dev.cristovantamayo.ecommerce.model.dto.ProductDTO;
import dev.cristovantamayo.ecommerce.listeners.GenericListener;
import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NamedNativeQueries({
        @NamedNativeQuery(name = "product_store.list",
                query = "select id, name, description, created_at, updated_at, price, photo " +
                        "from product_store", resultClass = Product.class),
        @NamedNativeQuery(name = "product_ecm.list",
                query = "select * from product_ecm", resultSetMapping = "product_ecm.Product")
})

@SqlResultSetMappings({
        @SqlResultSetMapping(name = "product_store.Product",
                entities = { @EntityResult(entityClass = Product.class) }),
        @SqlResultSetMapping(name = "product_ecm.Product",
                entities = { @EntityResult(entityClass = Product.class,
                fields = {
                        @FieldResult(name = "id", column = "prd_id"),
                        @FieldResult(name = "name", column = "prd_name"),
                        @FieldResult(name = "description", column = "prd_description"),
                        @FieldResult(name = "price", column = "prd_price"),
                        @FieldResult(name = "photo", column = "prd_photo"),
                        @FieldResult(name = "createdAt", column = "prd_created_at"),
                        @FieldResult(name = "updatedAt", column = "prd_updated_at")
                }) }),
        @SqlResultSetMapping(name ="product_ecm.ProductDTO",
                classes = {
                        @ConstructorResult(targetClass = ProductDTO.class,
                                columns = {
                                        @ColumnResult(name="prd_id", type = Integer.class),
                                        @ColumnResult(name="prd_name", type = String.class)
                                })
                })
})
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Product.list", query = "select p from Product p"),
        @NamedQuery(name = "Product.listByCategory", query = """
                select p from Product p 
                    where exists ( select 1 from Category c2
                                            join c2.products p2
                                        where p2 = p
                                            and c2.id = :categoryId )""")
})

@EntityListeners({ GenericListener.class })

@Entity
@Table(name = "product",
        uniqueConstraints = { @UniqueConstraint(name = "unq_product_name", columnNames = { "name" })},
        indexes = { @Index(name = "idx_product_name", columnList = "name") })
public class Product extends EntityBaseInteger {

    @Column(length = 100, nullable = false)
    private String name;

    @Lob // longtext
    private String description;

    public BigDecimal price;

    @ManyToMany() // cascade = CascadeType.MERGE
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_product_category_product")),
            inverseJoinColumns = @JoinColumn(name = "category_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_product_category_category")))
    @Column(name = "categories")
    private List<Category> categories;

    @OneToOne(mappedBy = "product")
    private Stock stock;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "product_tag",
            joinColumns = @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_tags")))
    @Column(name = "tag", length = 50, nullable = false)
    private List<String> tags;

    @ElementCollection
    @CollectionTable(name = "product_attribute",
            joinColumns = @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_attribute")))
    private List<Attribute> attributes;

    @Column(length = 20000)
    private byte[] photo;

    public static Product of (String name, String description, BigDecimal price,
                              List<Category> categories, Stock stock, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Product(name, description, price, categories, stock, createdAt, updatedAt, null, null, null);
    }
}
