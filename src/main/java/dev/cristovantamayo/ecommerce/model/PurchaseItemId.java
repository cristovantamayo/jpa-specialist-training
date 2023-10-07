package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import java.io.Serializable;
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PurchaseItemId implements Serializable {

    @EqualsAndHashCode.Include
    @Column(name = "purchase_id")
    private Integer purchaseId;

    @EqualsAndHashCode.Include
    @Column(name = "product_id")
    private Integer productId;

}
