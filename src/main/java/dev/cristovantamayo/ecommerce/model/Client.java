package dev.cristovantamayo.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SecondaryTable(name = "client_detail", pkJoinColumns = @PrimaryKeyJoinColumn(name = "client_id"))

@Entity
@Table(name="client",
        uniqueConstraints = { @UniqueConstraint(name = "unq_cpf", columnNames = {"cpf"}) },
        indexes = { @Index(name="idx_name", columnList = "name")})
public class Client extends EntityBaseInteger {

    private String name;

    @Transient
    private String firstName;

    private String cpf;

    @Column(table = "client_detail")
    @Enumerated(EnumType.STRING)
    private ClientGender gender;

    @Column(name = "birth_date", table = "client_detail")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "client")
    private List<Purchase> purchases;

    @ElementCollection
    @CollectionTable(name = "client_contact", joinColumns = @JoinColumn(name = "client_id"))
    @MapKeyColumn(name = "type")
    @Column(name = "description")
    private Map<String, String> contacts;

    @PostLoad
    public void configureFirstName() {
        if(name != null && !name.isEmpty()){
            String[] names = name.split(" ");
            firstName = names[0];
        }
    }

}
