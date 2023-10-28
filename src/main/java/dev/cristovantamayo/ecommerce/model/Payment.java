package dev.cristovantamayo.ecommerce.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@DiscriminatorColumn(name = "payment_type", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table(name = "payment")
public abstract class Payment extends EntityBaseInteger {

    @NotNull
    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "purchase_id", nullable = false, foreignKey = @ForeignKey(name = "fk_payment_purchase"))
    private Purchase purchase;

    @NotNull
    @Column(name = "payment_status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

}
