package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class Client {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private String name;

    public static Client of (Integer id, String name){
        return new Client(id, name);
    }

}
