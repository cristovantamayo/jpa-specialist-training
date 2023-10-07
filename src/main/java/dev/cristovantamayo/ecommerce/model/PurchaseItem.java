package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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

    @EmbeddedId
    private PurchaseItemId id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "purchase_id", insertable = false, updatable = false)
    private Purchase purchase;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    private Integer quantity;

    public static PurchaseItem of (Purchase purchase, Product product, BigDecimal productPrice, Integer quantity) {
        return new PurchaseItem(null, purchase, product, productPrice, quantity);
    }
}
