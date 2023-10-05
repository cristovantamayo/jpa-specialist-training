package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "purchase")
public class Purchase {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "purchase_due_date")
    private LocalDateTime purchaseDueDate;

    @Column(name = "invoice_id")
    private Integer invoiceId;

    private BigDecimal total;

    private PurchaseStatus status;

}
