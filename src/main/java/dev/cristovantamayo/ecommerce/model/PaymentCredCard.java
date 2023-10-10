package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@DiscriminatorValue("CredCard")
@NoArgsConstructor
@AllArgsConstructor

@Entity
//@Table(name = "payment_by_credcard") // Single Table Strategy
public class PaymentCredCard extends Payment{

    @Column(name = "card_number")
    private String cardNumber;

}
