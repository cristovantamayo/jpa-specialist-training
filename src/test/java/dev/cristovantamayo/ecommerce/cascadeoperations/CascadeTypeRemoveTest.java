package dev.cristovantamayo.ecommerce.cascadeoperations;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.model.PurchaseItem;
import dev.cristovantamayo.ecommerce.model.PurchaseItemId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CascadeTypeRemoveTest extends EntityManagerTest {

    //@Test
    public void removePurchaseAndPurchaseItems() {
        Purchase purchase = entityManager.find(Purchase.class, 1);

        entityManager.getTransaction().begin();
        entityManager.remove(purchase); // Necessário CascadeType.REMOVE no atributo "itens".
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assertions.assertNull(actualPurchase);
    }

    //@Test
    public void removePurchasePurchaseItemAndPurchase() {
        PurchaseItem purchaseItem = entityManager.find(
                PurchaseItem.class, new PurchaseItemId(1, 1));

        entityManager.getTransaction().begin();
        entityManager.remove(purchaseItem); // Necessário CascadeType.REMOVE no atributo "purchase".
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchaseItem.getPurchase().getId());
        Assertions.assertNull(actualPurchase);
    }
}
