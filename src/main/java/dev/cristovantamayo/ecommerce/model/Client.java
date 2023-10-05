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

    private GenderClient gender;

    public static Client of (Integer id, String name, GenderClient gender){
        return new Client(id, name, gender);
    }

}
