package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name="client")
public class Client {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ClientGender gender;

    @OneToMany(mappedBy = "client")
    private List<Purchase> purchases;

    public static Client of (Integer id, String name, ClientGender gender, List<Purchase> purchase){
        return new Client(id, name, gender, purchase);
    }

}
