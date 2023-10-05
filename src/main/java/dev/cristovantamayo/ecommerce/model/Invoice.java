package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class Invoice {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private Integer purchaseId;

    private String xml;

    private Date issueDate;

}
