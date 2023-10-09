package dev.cristovantamayo.ecommerce.AdvancedMapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.ClientGender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InheritanceTest extends EntityManagerTest {

    @Test
    public void saveClient() {
        Client client = new Client();
        client.setName("Mônica Belucci");
        client.setGender(ClientGender.WOMAN);

        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client actualClient = entityManager.find(Client.class, client.getId());
        Assertions.assertNotNull(actualClient.getId());
    }
}
