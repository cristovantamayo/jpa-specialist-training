package dev.cristovantamayo.ecommerce.model;

import jakarta.persistence.metamodel.StaticMetamodel;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedStoredProcedureQuery(name = "sold_above_average", procedureName = "sold_above_average",
        parameters = {
                @StoredProcedureParameter(name = "p_year", type = Integer.class, mode = ParameterMode.IN)
        },
        resultClasses = Client.class
)
@SecondaryTable(name = "client_detail",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "client_id"),
        foreignKey = @ForeignKey(name = "fk_client_detail_client"))
@Entity
@Table(name="client",
        uniqueConstraints = { @UniqueConstraint(name = "unq_cpf", columnNames = {"cpf"}) },
        indexes = { @Index(name="idx_name", columnList = "name")})
public class Client extends EntityBaseInteger {

    @NotBlank
    @Column(length = 100, nullable = false)
    private String name;

    @Transient
    private String firstName;

    @NotBlank
    @Column(length = 14, nullable = false)
    private String cpf;

    @Column(table = "client_detail", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientGender gender;

    @Column(name = "birth_date", table = "client_detail")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "client")
    private List<Purchase> purchases;

    @ElementCollection
    @CollectionTable(name = "client_contact",
            joinColumns = @JoinColumn(name = "client_id",
                    foreignKey = @ForeignKey(name = "fk_client_contacts")))
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
