package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private Integer id;

    @Column(name = "purchase_id")
    private Integer purchaseId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    private Integer quantity;
}
