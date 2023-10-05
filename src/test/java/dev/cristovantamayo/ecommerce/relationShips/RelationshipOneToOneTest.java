package dev.cristovantamayo.ecommerce.relationShips;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class RelationshipOneToOneTest extends EntityManagerTest {

    @Test
    public void relationshipValidationFailing() {
        Purchase purchase = entityManager.find(Purchase.class, 1);
        PaymentCredcard paymentCredcard = PaymentCredcard.of(null, purchase, PaymentStatus.RECEIVED, "1234");

        entityManager.getTransaction().begin();
        entityManager.persist(paymentCredcard);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());

        Assert.assertNotNull(actualPurchase.getPaymentCredcard());
    }


}
