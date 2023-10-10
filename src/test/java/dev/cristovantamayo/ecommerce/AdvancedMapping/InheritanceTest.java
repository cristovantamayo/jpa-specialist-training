package dev.cristovantamayo.ecommerce.AdvancedMapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InheritanceTest extends EntityManagerTest {

    @Test
    public void saveClient() {
        Client client = new Client();
        client.setName("Mônica Belucci");
        client.setGender(ClientGender.WOMAN);
        client.setCpf("1212121212");

        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client actualClient = entityManager.find(Client.class, client.getId());
        Assertions.assertNotNull(actualClient.getId());
    }

    @Test
    public void searchPayments() {
        List<Payment> payments =
                entityManager.createQuery("select p from Payment p")
                        .getResultList();

        System.out.println(payments);

        Assertions.assertFalse(payments.isEmpty());
    }

    @Test
    public void addPurchasePayment() {
        Purchase purchase = entityManager.find(Purchase.class, 1);

        PaymentCredCard paymentCredcard = new PaymentCredCard();
        paymentCredcard.setPurchase(purchase);
        paymentCredcard.setPaymentStatus(PaymentStatus.IN_PROCESS);
        paymentCredcard.setCardNumber("12345");

        entityManager.getTransaction().begin();
        entityManager.persist(paymentCredcard);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assertions.assertNotNull(actualPurchase.getPayment());
    }
}
