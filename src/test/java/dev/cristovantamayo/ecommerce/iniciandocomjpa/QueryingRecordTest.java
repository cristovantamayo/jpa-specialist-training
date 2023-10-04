package dev.cristovantamayo.ecommerce.iniciandocomjpa;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

public class QueryingRecordTest extends EntityManagerTest {

    @Test
    public void searchForObjectIdentifier() {
        Produto product =
                entityManager.find(Produto.class, 1);

        Produto productRef =
                entityManager.getReference(Produto.class, 1);

        System.out.println(productRef);

        Assert.assertNotNull(product);
        Assert.assertEquals("Kindle", product.getNome());
    }

    @Test
    public void UpdateProductReferenceTest() {
        Produto product =
                entityManager.find(Produto.class, 1);

        product.setNome("Microphone Samsung");

        // refresh restore object attributes from database
        entityManager.refresh(product);

        Assert.assertEquals("Kindle", product.getNome());
    }
}
