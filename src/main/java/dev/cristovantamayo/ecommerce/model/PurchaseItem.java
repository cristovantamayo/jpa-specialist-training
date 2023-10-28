package dev.cristovantamayo.ecommerce.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.MappedByteBuffer;

@Getter
@Setter
@SqlResultSetMappings({
        @SqlResultSetMapping(name="purchase_item-product.PurchaseItem-Product",
                entities = { @EntityResult(entityClass = PurchaseItem.class),
                        @EntityResult(entityClass = Product.class) })
})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "purchase_item")
public class PurchaseItem {

    @EmbeddedId
    private PurchaseItemId id;

    @NotNull
    @MapsId("purchaseId")
    @ManyToOne(optional = false) // , cascade = CascadeType.MERGE
    @JoinColumn(name = "purchase_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_purchase_Item_purchase"))
    private Purchase purchase;

    @NotNull
    @MapsId("productId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_purchase_item_product"))
    private Product product;

    @NotNull
    @Positive
    @Column(name = "product_price", nullable = false)
    private BigDecimal productPrice;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer quantity;

    public static PurchaseItem of (Purchase purchase, Product product, BigDecimal productPrice, Integer quantity) {
        return new PurchaseItem(null, purchase, product, productPrice, quantity);
    }
}
