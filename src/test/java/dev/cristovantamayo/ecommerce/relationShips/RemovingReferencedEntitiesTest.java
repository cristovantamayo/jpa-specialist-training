package dev.cristovantamayo.ecommerce.relationShips;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.Assert;
import org.junit.Test;

public class RemovingReferencedEntitiesTest extends EntityManagerTest {

    @Test
    public void relationshipValidation() {
        Purchase purchase = entityManager.find(Purchase.class, 1);
        Assert.assertFalse(purchase.getPurchaseItems().isEmpty());

        entityManager.getTransaction().begin();
        purchase.getPurchaseItems().forEach(i -> entityManager.remove(i));
        entityManager.remove(purchase);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assert.assertNull(actualPurchase);

    }
}
