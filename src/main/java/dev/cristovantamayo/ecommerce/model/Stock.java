package dev.cristovantamayo.ecommerce.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "stock", uniqueConstraints = {
        @UniqueConstraint(name = "unq_stock_product",
                columnNames = { "product_id"})} )
public class Stock extends EntityBaseInteger {

    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_stock_product"))
    private Product product;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer quantity;

    public static Stock of (Product product, Integer quantity) {
        return new Stock(product, quantity);
    }

}
