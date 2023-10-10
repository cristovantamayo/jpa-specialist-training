package dev.cristovantamayo.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
//@DiscriminatorColumn(name = "payment_type", discriminatorType = DiscriminatorType.STRING) // Table Per Class Strategy
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
//@Table(name = "payment") // Table Per Class Strategy
public abstract class Payment extends EntityBaseInteger {

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

}
