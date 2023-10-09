package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "invoice")
public class Invoice {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "purchase_id")
    private Integer id;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "purchase_id")
    //@JoinTable(name = "purchase_invoice",
    //        joinColumns = @JoinColumn(name = "invoice_id", unique = true),
    //        inverseJoinColumns = @JoinColumn(name = "purchase_id", unique = true))
    private Purchase purchase;

    @Lob
    @Column(length = 1000)
    private byte[] xml;

    @Column(name = "issue_date")
    private Date issueDate;

    public static Invoice of (Integer id, Purchase purchase, byte[] xml, Date issueDate){
        return new Invoice(id, purchase, xml, issueDate);
    }

}
