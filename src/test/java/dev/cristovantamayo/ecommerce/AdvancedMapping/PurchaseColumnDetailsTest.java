package dev.cristovantamayo.ecommerce.AdvancedMapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.Product;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PurchaseColumnDetailsTest extends EntityManagerTest {

    @Test
    public void avoidInsertionsOnUpdateAtColumn() {
        Client client = entityManager.find(Client.class, 1);
        Purchase purchase = new Purchase();
        purchase.setClient(client);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setUpdatedAt(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(purchase);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assertions.assertNotNull(actualPurchase.getPurchaseDate());
        Assertions.assertNull(actualPurchase.getUpdatedAt());
    }

    @Test
    public void avoidUpdateOnCreatedAtColumn() {
        entityManager.getTransaction().begin();

        Purchase purchase = entityManager.find(Purchase.class, 1);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setUpdatedAt(LocalDateTime.now());

        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase= entityManager.find(Purchase.class, purchase.getId());
        Assertions.assertNotEquals(purchase.getPurchaseDate().truncatedTo(ChronoUnit.SECONDS),
                actualPurchase.getPurchaseDate().truncatedTo(ChronoUnit.SECONDS));
        Assertions.assertEquals(purchase.getUpdatedAt().truncatedTo(ChronoUnit.SECONDS),
                actualPurchase.getUpdatedAt().truncatedTo(ChronoUnit.SECONDS));
    }
}
