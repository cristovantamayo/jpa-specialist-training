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

    @MapsId("purchaseId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @MapsId("productId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_price", nullable = false)
    private BigDecimal productPrice;

    @Column(nullable = false)
    private Integer quantity;

    public static PurchaseItem of (Purchase purchase, Product product, BigDecimal productPrice, Integer quantity) {
        return new PurchaseItem(null, purchase, product, productPrice, quantity);
    }
}
