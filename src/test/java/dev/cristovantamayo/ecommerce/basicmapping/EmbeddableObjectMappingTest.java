package dev.cristovantamayo.ecommerce.basicmapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class EmbeddableObjectMappingTest extends EntityManagerTest {

    @Test
    public void EmbeddableObjectMappingAnalysis() {
        Client client = entityManager.find(Client.class, 1);

        DeliveryAddress deliveryAddress =
                DeliveryAddress.of("08990-010", "Jefferson Sr", "2376",
                        "Apt 2", "Elwood Park", "Baltimore", "Maryland");

        PurchaseItem purchaseItem = entityManager.find(PurchaseItem.class, 1);

        Purchase purchase = Purchase.of(null, client, LocalDateTime.now(), null,null, null,
                new BigDecimal(4000), Arrays.asList(purchaseItem), PurchaseStatus.WAITING, deliveryAddress, null);

        entityManager.getTransaction().begin();
        entityManager.persist(purchase);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assert.assertNotNull(actualPurchase);
    }
}
