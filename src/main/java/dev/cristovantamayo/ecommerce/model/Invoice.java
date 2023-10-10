package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "invoice")
public class Invoice extends EntityBaseInteger {

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "purchase_id")
    //@JoinTable(name = "purchase_invoice",
    //        joinColumns = @JoinColumn(name = "invoice_id", unique = true),
    //        inverseJoinColumns = @JoinColumn(name = "purchase_id", unique = true))
    private Purchase purchase;

    @Lob
    @Column(length = 1000, nullable = false)
    private byte[] xml;

    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

}
