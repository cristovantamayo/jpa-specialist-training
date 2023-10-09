package dev.cristovantamayo.ecommerce.AdvancedMapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Attribute;
import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
}
