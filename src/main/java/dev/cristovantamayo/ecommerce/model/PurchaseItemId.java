package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Id;
import java.io.Serializable;
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PurchaseItemId implements Serializable {

    @EqualsAndHashCode.Include
    @Column(name = "purchase_id", nullable = false)
    private Integer purchaseId;

    @EqualsAndHashCode.Include
    @Column(name = "product_id", nullable = false)
    private Integer productId;

}
