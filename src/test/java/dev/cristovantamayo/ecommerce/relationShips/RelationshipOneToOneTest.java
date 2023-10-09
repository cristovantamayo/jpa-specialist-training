package dev.cristovantamayo.ecommerce.relationShips;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Invoice;
import dev.cristovantamayo.ecommerce.model.PaymentCredcard;
import dev.cristovantamayo.ecommerce.model.PaymentStatus;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

        Assertions.assertNotNull(actualPurchase.getPaymentCredcard());
    }

    @Test
    public void relationshipValidationInvoicePurchase() {
        Purchase purchase = entityManager.find(Purchase.class, 1);
        Invoice invoice = new Invoice();
        invoice.setPurchase(purchase);
        invoice.setXml("<?xml><![CDATA[1234]]></xml>".getBytes());
        invoice.setIssueDate(new Date());

        entityManager.getTransaction().begin();
        entityManager.persist(invoice);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assertions.assertNotNull(actualPurchase.getInvoice());
    }


}
