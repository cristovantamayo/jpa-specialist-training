package dev.cristovantamayo.ecommerce.AdvancedMapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ColumnDetailsTest extends EntityManagerTest {

    @Test
    public void avoidInsertionsOnUpdateAtColumn() {
        Product product = new Product();
        product.setName("keyboard for smartphone");
        product.setDescription("The most comfortable");
        product.setPrice(BigDecimal.ONE);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product actualProduct = entityManager.find(Product.class, product.getId());
        Assertions.assertNotNull(actualProduct.getCreatedAt());
        Assertions.assertNull(actualProduct.getUpdatedAt());
    }

    @Test
    public void avoidUpdateOnCreatedAtColumn() {
        entityManager.getTransaction().begin();

        Product product = entityManager.find(Product.class, 1);
        product.setPrice(BigDecimal.TEN);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        entityManager.getTransaction().commit();

        entityManager.clear();

        Product actualProduct = entityManager.find(Product.class, product.getId());
        Assertions.assertNotEquals(product.getCreatedAt().truncatedTo(ChronoUnit.SECONDS),
                actualProduct.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
        Assertions.assertEquals(product.getUpdatedAt().truncatedTo(ChronoUnit.SECONDS),
                actualProduct.getUpdatedAt().truncatedTo(ChronoUnit.SECONDS));
    }
}
