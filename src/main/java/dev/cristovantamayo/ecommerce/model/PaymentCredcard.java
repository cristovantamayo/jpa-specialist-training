package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private Integer id;

    @Column(name = "purchase_id")
    private Integer purchaseId;

    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "card_number")
    private String cardNumber;

}
