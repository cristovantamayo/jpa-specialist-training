package dev.cristovantamayo.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DeliveryAddress {

    private String cep;

    private String street;

    private String number;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;

    public static DeliveryAddress of (String cep, String street, String number, String complement, String neighborhood, String city, String state){
        return new DeliveryAddress(cep, street, number, complement, neighborhood, city, state);
    }
}
