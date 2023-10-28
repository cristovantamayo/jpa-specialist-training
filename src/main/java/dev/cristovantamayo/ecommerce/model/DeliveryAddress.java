package dev.cristovantamayo.ecommerce.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotNull
    @Pattern(regexp = "[0-9]{5}-[0-9]{3}")
    @Column(length = 9)
    private String cep;

    @NotBlank
    @Column(length = 100)
    private String street;

    @NotBlank
    @Column(length = 10)
    private String number;

    @Column(length = 50)
    private String complement;

    @NotBlank
    @Column(length = 50)
    private String neighborhood;

    @NotBlank
    @Column(length = 50)
    private String city;

    @NotBlank
    @Size(max = 2, min = 2)
    @Column(length = 2)
    private String state;

    public static DeliveryAddress of (String cep, String street, String number, String complement, String neighborhood, String city, String state){
        return new DeliveryAddress(cep, street, number, complement, neighborhood, city, state);
    }
}
