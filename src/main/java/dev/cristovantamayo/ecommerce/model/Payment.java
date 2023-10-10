package dev.cristovantamayo.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@DiscriminatorColumn(name = "payment_type", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "payment")
public abstract class Payment extends EntityBaseInteger {

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

}
