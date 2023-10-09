package dev.cristovantamayo.ecommerce.AdvancedMapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Attribute;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

public class ElementCollectionTest extends EntityManagerTest {

    @Test
    public void applyTags() {
        entityManager.getTransaction().begin();

        Product product = entityManager.find(Product.class, 1);
        product.setTags(Arrays.asList("eBook", "Digital Reader"));

        entityManager.getTransaction().commit();

        entityManager.clear();

        Product actualProduct = entityManager.find(Product.class, product.getId());
        Assertions.assertFalse(actualProduct.getTags().isEmpty());
    }

    @Test
    public void applyAttributes() {
        entityManager.getTransaction().begin();

        Product product = entityManager.find(Product.class, 1);
        product.setAttributes(Arrays.asList(Attribute.of("screen", "12 pol")));

        entityManager.getTransaction().commit();

        entityManager.clear();

        Product actualProduct = entityManager.find(Product.class, product.getId());
        Assertions.assertFalse(actualProduct.getAttributes().isEmpty());
    }

    @Test
    public void applyContacts() {

        final String email = "fernando@email.com";

        entityManager.getTransaction().begin();

        Client client = entityManager.find(Client.class, 1);
        client.setContacts(Collections.singletonMap("email", email));

        entityManager.getTransaction().commit();

        entityManager.clear();

        Client actualClient = entityManager.find(Client.class, client.getId());
        Assertions.assertFalse(actualClient.getContacts().isEmpty());
        Assertions.assertEquals(email, actualClient.getContacts().get("email"));
    }
}
