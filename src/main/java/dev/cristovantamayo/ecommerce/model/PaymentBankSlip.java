package dev.cristovantamayo.ecommerce.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDate;

@Getter
@Setter
@DiscriminatorValue("BankSlip")
@NoArgsConstructor
@AllArgsConstructor

@Entity
//@Table(name = "payment_by_bankslip") // Single Table Strategy
public class PaymentBankSlip extends Payment {

    @NotNull
    @Column(name = "bar_code", length = 100)
    private String barCode;

    @NotNull
    @FutureOrPresent
    @Column(name = "due_date")
    private LocalDate dueDate;

}
