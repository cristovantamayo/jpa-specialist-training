package dev.cristovantamayo.ecommerce.relationShips;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class RelationshipOneToManyTest extends EntityManagerTest {

    @Test
    public void relationshipValidation() {
        Client client = entityManager.find(Client.class, 1);

        PurchaseItem purchaseItem = entityManager.find(PurchaseItem.class, 1);

        Purchase purchase = Purchase.of(null, client,
                LocalDateTime.now(), LocalDateTime.now(), null,
                new BigDecimal(5000), Arrays.asList(purchaseItem), PurchaseStatus.WAITING, DeliveryAddress.of("08990-010",
                        "Jefferson Sr", "2376", "Apt 2",
                        "Elwood Park", "Baltimore", "Maryland"));

        entityManager.getTransaction().begin();
        entityManager.persist(purchase);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client actualClient = entityManager.find(Client.class, client.getId());

        Assert.assertFalse(actualClient.getPurchases().isEmpty());

    }
}
