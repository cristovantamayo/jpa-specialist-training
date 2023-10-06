package dev.cristovantamayo.ecommerce.relationShips;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

public class RelationshipOneToOneTest extends EntityManagerTest {

    @Test
    public void relationshipValidation() {
        Purchase purchase = entityManager.find(Purchase.class, 1);
        PaymentCredcard paymentCredcard = PaymentCredcard.of(null, purchase, PaymentStatus.RECEIVED, "1234");

        entityManager.getTransaction().begin();
        entityManager.persist(paymentCredcard);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());

        Assert.assertNotNull(actualPurchase.getPaymentCredcard());
    }

    @Test
    public void relationshipValidationInvoicePurchase() {
        Purchase purchase = entityManager.find(Purchase.class, 1);
        Invoice invoice = Invoice.of(null, purchase, "<?xml><![CDATA[1234]]></xml>", new Date());

        entityManager.getTransaction().begin();
        entityManager.persist(invoice);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assert.assertNotNull(actualPurchase.getInvoice());
    }


}
