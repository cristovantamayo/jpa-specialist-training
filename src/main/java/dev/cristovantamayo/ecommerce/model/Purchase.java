package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "purchase_due_date")
    private LocalDateTime purchaseDueDate;

    @Column(name = "invoice_id")
    private Integer invoiceId;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    @Embedded
    private DeliveryAddress deliveryAddress;

    public static Purchase of (Integer id, LocalDateTime purchaseDate, LocalDateTime purchaseDueDate,
                               Integer invoiceId, BigDecimal total, PurchaseStatus status, DeliveryAddress deliveryAddress) {
        return new Purchase(id, purchaseDate, purchaseDueDate, invoiceId, total, status, deliveryAddress);
    }

}
