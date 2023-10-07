package dev.cristovantamayo.ecommerce.gettingtoknowentitymanager;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.Product;
import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.model.PurchaseStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.color.ProfileDataException;

public class ListenersTest extends EntityManagerTest {

    @Test
    public void loadEntities() {
        Product product = entityManager.find(Product.class, 1);
        Purchase purchase = entityManager.find(Purchase.class, 1);
    }

    @Test
    public void triggerCallbacks() {
        Client client = entityManager.find(Client.class, 1);
        Purchase purchase = new Purchase();

        purchase.setClient(client);
        purchase.setStatus(PurchaseStatus.WAITING);

        entityManager.getTransaction().begin();

        entityManager.persist(purchase);
        entityManager.flush();

        purchase.setStatus(PurchaseStatus.PAID_OUT);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assertions.assertNotNull(actualPurchase.getPurchaseDate());
        Assertions.assertNotNull(actualPurchase.getUpdatedAt());
    }
}
