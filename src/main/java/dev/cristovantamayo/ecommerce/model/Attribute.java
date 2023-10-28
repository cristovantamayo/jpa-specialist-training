package dev.cristovantamayo.ecommerce.model;

import jakarta.validation.constraints.NotBlank;
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
public class Attribute {

    @NotBlank
    @Column(length = 100, nullable = false)
    private String name;

    @NotBlank
    private String value;

    public static Attribute of (String name, String value) {
        return new Attribute(name, value);
    }
}
