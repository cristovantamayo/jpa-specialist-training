package dev.cristovantamayo.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode

@Entity
public class Produto {

    @Id
    private Integer id;

    private String nome;

    private String descricao;

    public BigDecimal preco;
}
