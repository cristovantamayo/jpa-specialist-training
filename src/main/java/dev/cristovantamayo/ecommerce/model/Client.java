package dev.cristovantamayo.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Client {

    @Id
    private Integer id;

    private String name;

    public static Client of (Integer id, String name){
        return new Client(id, name);
    }

}
