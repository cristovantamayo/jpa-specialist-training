package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "payment_by_credcard")
public class PaymentCredcard {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "card_number")
    private String cardNumber;

    public static PaymentCredcard of (Integer id, Purchase purchase, PaymentStatus paymentStatus, String cardNumber){
        return new PaymentCredcard(id, purchase, paymentStatus, cardNumber);
    }

}
