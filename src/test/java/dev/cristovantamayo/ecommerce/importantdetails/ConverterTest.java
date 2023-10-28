package dev.cristovantamayo.ecommerce.importantdetails;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ConverterTest extends EntityManagerTest {

    @Test
    public void converter() {
        Product product = new Product();
        product.setCreatedAt(LocalDateTime.now());
        product.setName("Carregador de Notebook Dell");
        product.setActive(Boolean.TRUE);
        product.setPrice(new BigDecimal(160));

        entityManager.getTransaction().begin();

        entityManager.persist(product);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Product productVerification = entityManager.find(Product.class, product.getId());
        Assertions.assertTrue(productVerification.getActive());
    }
}
