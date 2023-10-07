package dev.cristovantamayo.ecommerce.relationShips;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RemovingReferencedEntitiesTest extends EntityManagerTest {

    @Test
    public void relationshipValidation() {
        Purchase purchase = entityManager.find(Purchase.class, 1);
        Assertions.assertFalse(purchase.getPurchaseItems().isEmpty());

        entityManager.getTransaction().begin();
        purchase.getPurchaseItems().forEach(i -> entityManager.remove(i));
        entityManager.remove(purchase);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assertions.assertNull(actualPurchase);

    }
}
