package dev.cristovantamayo.ecommerce.AdvancedMapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.ClientGender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class SecondaryTableTest extends EntityManagerTest {

    @Test
    public void saveClient() {
        Client client = new Client();
        client.setName("Carlos Firmino de Arruda");
        client.setGender(ClientGender.MAN);
        client.setBirthDate(LocalDate.of(1979, 7, 26));
        client.setCpf("44444444444");

        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client atualClient = entityManager.find(Client.class, client.getId());
        Assertions.assertNotNull(atualClient.getGender());
    }
}
