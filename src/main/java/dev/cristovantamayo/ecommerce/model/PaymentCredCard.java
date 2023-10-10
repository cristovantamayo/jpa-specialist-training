package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "payment_by_credcard")
public class PaymentCredCard extends Payment{

    @Column(name = "card_number")
    private String cardNumber;

}
