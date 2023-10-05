package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class PurchaseItem {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private Integer purchaseId;

    private Integer productId;

    private BigDecimal productPrice;

    private Integer quantity;
}
