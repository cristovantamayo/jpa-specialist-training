package dev.cristovantamayo.ecommerce.basicmapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.ClientGender;
import org.junit.jupiter.api.Test;

public class BasicMappingTest extends EntityManagerTest {

    @Test
    public void testarEnum() {
        Client client = Client.of(null, "José Mineiro", ClientGender.MAN, null);

        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client actualClient = entityManager.find(Client.class, client.getId());

    }

}
