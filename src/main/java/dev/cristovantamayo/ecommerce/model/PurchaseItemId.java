package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import java.io.Serializable;
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemId implements Serializable {

    @EqualsAndHashCode.Include
    private Integer purchaseId;

    @EqualsAndHashCode.Include
    private Integer productId;
}
