package dev.cristovantamayo.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@DiscriminatorColumn(name = "payment_type", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table(name = "payment")
public abstract class Payment extends EntityBaseInteger {

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "purchase_id", nullable = false, foreignKey = @ForeignKey(name = "fk_payment_purchase"))
    private Purchase purchase;

    @Column(name = "payment_status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

}
