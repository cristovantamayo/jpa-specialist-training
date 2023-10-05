package dev.cristovantamayo.ecommerce.basicmapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.DeliveryAddress;
import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.model.PurchaseStatus;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EmbeddableObjectMappingTest extends EntityManagerTest {

    @Test
    public void EmbeddableObjectMappingAnalysis() {
        Client client = entityManager.find(Client.class, 1);

        DeliveryAddress deliveryAddress =
                DeliveryAddress.of("08990-010", "Jefferson Sr", "2376",
                        "Apt 2", "Elwood Park", "Baltimore", "Maryland");

        Purchase purchase = Purchase.of(null, client, LocalDateTime.now(), null, null,
                new BigDecimal(4000), PurchaseStatus.WAITING, deliveryAddress);

        entityManager.getTransaction().begin();
        entityManager.persist(purchase);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assert.assertNotNull(actualPurchase);
    }
}
