package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "payment_by_ticket")
public class PaymentTicket extends Payment {

    @Column(name = "bar_code")
    private String barCode;

}
