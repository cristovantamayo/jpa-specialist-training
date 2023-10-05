package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    private ClientGender gender;

    public static Client of (Integer id, String name, ClientGender gender){
        return new Client(id, name, gender);
    }

}
