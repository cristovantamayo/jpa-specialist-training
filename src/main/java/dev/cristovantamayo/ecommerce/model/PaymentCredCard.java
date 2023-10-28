package dev.cristovantamayo.ecommerce.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@DiscriminatorValue("CredCard")
@NoArgsConstructor
@AllArgsConstructor

@Entity
//@Table(name = "payment_by_credcard") // Single Table Strategy
public class PaymentCredCard extends Payment{

    //@NotBlank
    @NotEmpty
    @Column(name = "card_number", length = 50)
    private String cardNumber;

}
