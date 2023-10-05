package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @Column(name = "purchase_id")
    private Integer purchaseId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    private Integer quantity;

    public static PurchaseItem of (Integer id, Integer purchaseId, Integer productId, BigDecimal productPrice, Integer quantity) {
        return new PurchaseItem(id, purchaseId, productId, productPrice, quantity);
    }
}
