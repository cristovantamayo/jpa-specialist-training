package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class Purchase {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private LocalDateTime purchaseDate;

    private LocalDateTime purchaseDue;

    private Integer invoiceId;

    private BigDecimal total;

    private PurchaseStatus status;

}
