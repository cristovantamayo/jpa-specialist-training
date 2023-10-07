package dev.cristovantamayo.ecommerce.gettingtoknowentitymanager;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class PersistenceContext extends EntityManagerTest {

    @Test
    public void useExplicitPersistenceContext() {
        entityManager.getTransaction().begin();

        Product managedProduct = entityManager.find(Product.class, 1);
        // JPA will do Dirty Check before Flush
        managedProduct.setPrice(new BigDecimal(350.00));

        Product product = new Product();
        product.setName("Coffee Cup");
        product.setDescription("God to drink Coffee");
        product.setPrice(new BigDecimal(89.00));
        entityManager.persist(product);

        Product product2 = new Product();
        product2.setName("Tea Cup");
        product2.setDescription("God to drink Tea");
        product2.setPrice(new BigDecimal(89.00));
        product2 = entityManager.merge(product2);

        entityManager.flush();

        product2.setDescription("God to drink Tea - 550 ml");

        entityManager.getTransaction().commit();

    }
}
