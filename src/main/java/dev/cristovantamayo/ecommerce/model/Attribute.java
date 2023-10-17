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
public class Attribute {

    @Column(length = 100, nullable = false)
    private String name;

    private String value;

    public static Attribute of (String name, String value) {
        return new Attribute(name, value);
    }
}
