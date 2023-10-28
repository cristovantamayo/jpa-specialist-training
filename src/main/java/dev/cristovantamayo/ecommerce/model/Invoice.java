package dev.cristovantamayo.ecommerce.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "invoice")
public class Invoice extends EntityBaseInteger {

    @NotNull
    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "purchase_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_invoice_purchase"))
    //@JoinTable(name = "purchase_invoice",
    //        joinColumns = @JoinColumn(name = "invoice_id", unique = true),
    //        inverseJoinColumns = @JoinColumn(name = "purchase_id", unique = true))
    private Purchase purchase;

    @NotEmpty
    @Lob
    @Column(length = 1000, nullable = false)
    private byte[] xml;

    @NotNull
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

}
