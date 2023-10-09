package dev.cristovantamayo.ecommerce.AdvancedMapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransientPropertiesTest extends EntityManagerTest {

    @Test
    public void firstNameValidation() {
        Client client = entityManager.find(Client.class, 1);
        Assertions.assertEquals("Fernando", client.getFirstName());
    }
}
