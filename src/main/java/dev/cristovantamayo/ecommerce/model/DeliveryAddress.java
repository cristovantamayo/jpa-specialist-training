package dev.cristovantamayo.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DeliveryAddress {

    @Column(length = 9)
    private String cep;

    @Column(length = 100)
    private String street;

    @Column(length = 10)
    private String number;

    @Column(length = 50)
    private String complement;

    @Column(length = 50)
    private String neighborhood;

    @Column(length = 50)
    private String city;

    @Column(length = 2)
    private String state;

    public static DeliveryAddress of (String cep, String street, String number, String complement, String neighborhood, String city, String state){
        return new DeliveryAddress(cep, street, number, complement, neighborhood, city, state);
    }
}
