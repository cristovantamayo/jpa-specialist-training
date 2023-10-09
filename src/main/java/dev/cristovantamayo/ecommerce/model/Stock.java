package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "stock")
public class Stock extends EntityBaseInteger {

    @OneToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    public static Stock of (Product product, Integer quantity) {
        return new Stock(product, quantity);
    }

}
