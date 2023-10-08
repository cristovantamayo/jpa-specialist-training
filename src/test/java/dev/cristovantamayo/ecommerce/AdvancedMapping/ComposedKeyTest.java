package dev.cristovantamayo.ecommerce.AdvancedMapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ComposedKeyTest extends EntityManagerTest {

    @Test
    public void saveItem() {
        entityManager.getTransaction().begin();

        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Purchase purchase = new Purchase();
        purchase.setClient(client);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setStatus(PurchaseStatus.WAITING);
        purchase.setTotal(product.getPrice());

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setId(new PurchaseItemId());
        purchaseItem.setPurchase(purchase);
        purchaseItem.setProduct(product);
        purchaseItem.setProductPrice(product.getPrice());
        purchaseItem.setQuantity(1);

        entityManager.persist(purchaseItem);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assertions.assertNotNull(actualPurchase);
        Assertions.assertFalse(actualPurchase.getPurchaseItems().isEmpty());
    }

    @Test
    public void searchItem() {
        PurchaseItem purchaseItem = entityManager.find(
                PurchaseItem.class, new PurchaseItemId(1, 1));

        Assertions.assertNotNull(purchaseItem);
    }
}
