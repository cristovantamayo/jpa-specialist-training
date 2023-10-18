package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDate;

@Getter
@Setter
@DiscriminatorValue("Ticket")
@NoArgsConstructor
@AllArgsConstructor

@Entity
//@Table(name = "payment_by_ticket") // Single Table Strategy
public class PaymentTicket extends Payment {

    @Column(name = "bar_code", length = 100)
    private String barCode;

    @Column(name = "due_date")
    private LocalDate dueDate;

}
