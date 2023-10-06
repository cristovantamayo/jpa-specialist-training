package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.nio.MappedByteBuffer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "purchase_item")
public class PurchaseItem {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    private Integer quantity;

    public static PurchaseItem of (Integer id, Purchase purchase, Product product, BigDecimal productPrice, Integer quantity) {
        return new PurchaseItem(id, purchase, product, productPrice, quantity);
    }
}
