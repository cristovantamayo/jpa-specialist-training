package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "payment_by_ticket")
public class PaymentTicket {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "purchase_id")
    private Integer purchaseId;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "bar_code")
    private String barCode;

    public static PaymentTicket of (Integer id, Integer purchaseId, PaymentStatus paymentStatus, String barCode){
        return new PaymentTicket(id, purchaseId, paymentStatus, barCode);
    }

}
