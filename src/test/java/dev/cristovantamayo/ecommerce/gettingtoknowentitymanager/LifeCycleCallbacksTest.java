package dev.cristovantamayo.ecommerce.gettingtoknowentitymanager;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.model.PurchaseStatus;
import org.junit.Assert;
import org.junit.Test;

public class LifeCycleCallbacksTest extends EntityManagerTest {

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
        Assert.assertNotNull(actualPurchase.getPurchaseDate());
        Assert.assertNotNull(actualPurchase.getUpdatedAt());
    }
}
