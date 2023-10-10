package dev.cristovantamayo.ecommerce.relationShips;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class RelationshipOneToManyTest extends EntityManagerTest {

    @Test
    public void relationshipValidation() {
        Client client = entityManager.find(Client.class, 1);


        Purchase purchase = new Purchase();
        purchase.setClient(client);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setTotal(new BigDecimal(5000));
        purchase.setStatus(PurchaseStatus.WAITING);
        purchase.setDeliveryAddress(DeliveryAddress.of("08990-010",
                        "Jefferson Sr", "2376", "Apt 2",
                        "Elwood Park", "Baltimore", "MD"));

        entityManager.getTransaction().begin();
        entityManager.persist(purchase);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client actualClient = entityManager.find(Client.class, client.getId());

        Assertions.assertFalse(actualClient.getPurchases().isEmpty());

    }

    @Test
    public void verifyRelationshipPurchase() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Purchase purchase = new Purchase();
        purchase.setStatus(PurchaseStatus.WAITING);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setTotal(BigDecimal.TEN);
        purchase.setClient(client);

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setId(new PurchaseItemId());
        purchaseItem.setProductPrice(product.getPrice());
        purchaseItem.setQuantity(1);
        purchaseItem.setPurchase(purchase);
        purchaseItem.setProduct(product);

        entityManager.getTransaction().begin();
        entityManager.persist(purchase);
        entityManager.persist(purchaseItem);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase ActualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assertions.assertFalse(ActualPurchase.getPurchaseItems().isEmpty());
    }
}
