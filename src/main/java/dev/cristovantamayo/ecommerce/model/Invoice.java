package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "Invoice")
public class Invoice {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    @Column(name = "purchase_id")
    private Integer purchaseId;

    private String xml;

    @Column(name = "issue_date")
    private Date issueDate;

}
