package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private Integer id;

    private String name;

    private GenderClient gender;

    public static Client of (Integer id, String name, GenderClient gender){
        return new Client(id, name, gender);
    }

}
