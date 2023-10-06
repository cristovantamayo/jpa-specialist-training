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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(optional = false)
    @JoinColumn(name = "purchase_id")
    //@JoinTable(name = "purchase_invoice",
    //        joinColumns = @JoinColumn(name = "invoice_id", unique = true),
    //        inverseJoinColumns = @JoinColumn(name = "purchase_id", unique = true))
    private Purchase purchase;

    private String xml;

    @Column(name = "issue_date")
    private Date issueDate;

    public static Invoice of (Integer id, Purchase purchase, String xml, Date issueDate){
        return new Invoice(id, purchase, xml, issueDate);
    }

}