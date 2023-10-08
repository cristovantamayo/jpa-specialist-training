package dev.cristovantamayo.ecommerce.relationShips;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class RelationshipManyToOneTest extends EntityManagerTest {

    @Test
    public void relationshipValidation() {

        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Purchase purchase = new Purchase();
        purchase.setStatus(PurchaseStatus.WAITING);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setClient(client);
        purchase.setTotal(new BigDecimal(5000));
        purchase.setDeliveryAddress(DeliveryAddress.of("08990-010",
                "Jefferson Sr", "2376", "Apt 2",
                "Elwood Park", "Baltimore", "Maryland"));

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setId(new PurchaseItemId());
        purchaseItem.setProductPrice(product.getPrice());
        purchaseItem.setQuantity(1);
        purchaseItem.setPurchase(purchase);
        purchaseItem.setProduct(product);

        entityManager.getTransaction().begin();
        entityManager.persist(purchaseItem);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assertions.assertNotNull(actualPurchase.getClient());
        Assertions.assertEquals(purchase.getDeliveryAddress().getNeighborhood(),
                actualPurchase.getDeliveryAddress().getNeighborhood());

        PurchaseItem actualItem = entityManager.find(PurchaseItem.class,purchaseItem.getId());
        Assertions.assertNotNull(actualItem.getPurchase());
        Assertions.assertNotNull(actualItem.getProduct());

    }
}
