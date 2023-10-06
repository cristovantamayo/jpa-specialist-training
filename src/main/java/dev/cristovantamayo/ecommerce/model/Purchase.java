package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "purchase_due_date")
    private LocalDateTime purchaseDueDate;

    @OneToOne(mappedBy = "purchase")
    private Invoice invoice;

    private BigDecimal total;

    @OneToMany(mappedBy = "purchase")
    @Column(name = "purchase_item")
    private List<PurchaseItem> purchaseItems;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    @Embedded
    @Column(name = "delivery_address")
    private DeliveryAddress deliveryAddress;

    @OneToOne(mappedBy = "purchase")
    private PaymentCredcard paymentCredcard;

    public static Purchase of (Integer id, Client client, LocalDateTime purchaseDate, LocalDateTime purchaseDueDate,
                               Invoice invoice, BigDecimal total, List<PurchaseItem> purchaseItems,
                               PurchaseStatus status, DeliveryAddress deliveryAddress, PaymentCredcard paymentCredcard) {

        return new Purchase(id, client, purchaseDate, purchaseDueDate, invoice, total,
                purchaseItems, status, deliveryAddress, paymentCredcard);
    }

}
