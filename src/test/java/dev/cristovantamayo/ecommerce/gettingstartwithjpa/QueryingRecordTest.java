package dev.cristovantamayo.ecommerce.gettingstartwithjpa;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QueryingRecordTest extends EntityManagerTest {

    @Test
    public void searchForObjectIdentifier() {
        Product product =
                entityManager.find(Product.class, 1);

        Product productRef =
                entityManager.getReference(Product.class, 1);

        System.out.println(productRef);

        Assertions.assertNotNull(product);
        Assertions.assertEquals("Kindle", product.getName());
    }

    @Test
    public void UpdateProductReferenceTest() {
        Product product =
                entityManager.find(Product.class, 1);

        product.setName("Microphone Samsung");

        // refresh restore object attributes from database
        entityManager.refresh(product);

        Assertions.assertEquals("Kindle", product.getName());
    }
}
