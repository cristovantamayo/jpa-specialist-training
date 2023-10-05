package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "stock")
public class Stock {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    @Column(name = "product_id")
    private Integer productId;

    private Integer quantity;

    public static Stock of (Integer id, Integer productId, Integer quantity) {
        return new Stock(id, productId, quantity);
    }

}
