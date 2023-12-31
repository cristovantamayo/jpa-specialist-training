package dev.cristovantamayo.ecommerce.basicmapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class EmbeddableObjectMappingTest extends EntityManagerTest {

    @Test
    public void EmbeddableObjectMappingAnalysis() {
        Client client = entityManager.find(Client.class, 1);

        DeliveryAddress deliveryAddress =
                DeliveryAddress.of("08990-010", "Jefferson Sr", "2376",
                        "Apt 2", "Elwood Park", "Baltimore", "MD");

        PurchaseItem purchaseItem = entityManager.find(PurchaseItem.class, new PurchaseItemId(1, 1));

        Payment payment = entityManager.find(Payment.class, 1);

        Purchase purchase = Purchase.of(client, LocalDateTime.now(), null, null, null,
                new BigDecimal(4000), Arrays.asList(purchaseItem), PurchaseStatus.WAITING, deliveryAddress, payment);

        entityManager.getTransaction().begin();
        entityManager.persist(purchase);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assertions.assertNotNull(actualPurchase);
    }
}
