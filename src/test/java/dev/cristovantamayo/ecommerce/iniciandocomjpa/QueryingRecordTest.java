package dev.cristovantamayo.ecommerce.iniciandocomjpa;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.Assert;
import org.junit.Test;

public class QueryingRecordTest extends EntityManagerTest {

    @Test
    public void searchForObjectIdentifier() {
        Product product =
                entityManager.find(Product.class, 1);

        Product productRef =
                entityManager.getReference(Product.class, 1);

        System.out.println(productRef);

        Assert.assertNotNull(product);
        Assert.assertEquals("Kindle", product.getName());
    }

    @Test
    public void UpdateProductReferenceTest() {
        Product product =
                entityManager.find(Product.class, 1);

        product.setName("Microphone Samsung");

        // refresh restore object attributes from database
        entityManager.refresh(product);

        Assert.assertEquals("Kindle", product.getName());
    }
}
