package dev.cristovantamayo.ecommerce.advancedmapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ExerciceMapsIdTest extends EntityManagerTest {

    @Test
    public void insertPayment() {
        Purchase purchase = entityManager.find(Purchase.class, 1);

        PaymentCredCard payment = new PaymentCredCard();
        payment.setPurchase(purchase);
        payment.setCardNumber("1234");
        payment.setPaymentStatus(PaymentStatus.IN_PROCESS);

        entityManager.getTransaction().begin();
        entityManager.persist(payment);
        entityManager.getTransaction().commit();

        entityManager.clear();

        PaymentCredCard actualPayment = entityManager.find(PaymentCredCard.class, payment.getId());
        Assertions.assertNotNull(actualPayment);
        Assertions.assertEquals(purchase.getId(), actualPayment.getId());
    }

    @Test
    public void insertPurchaseItem() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Purchase purchase = new Purchase();
        purchase.setClient(client);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setStatus(PurchaseStatus.WAITING);
        purchase.setTotal(product.getPrice());

        PurchaseItem itemPurchase = new PurchaseItem();
        itemPurchase.setId(new PurchaseItemId());
        itemPurchase.setPurchase(purchase);
        itemPurchase.setProduct(product);
        itemPurchase.setProductPrice(product.getPrice());
        itemPurchase.setQuantity(1);

        entityManager.getTransaction().begin();
        entityManager.persist(itemPurchase);
        entityManager.getTransaction().commit();

        entityManager.clear();

        PurchaseItem actualPurchaseItem = entityManager.find(
                PurchaseItem.class, new PurchaseItemId(purchase.getId(), product.getId()));

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());

        Assertions.assertNotNull(actualPurchase);
        Assertions.assertNotNull(actualPurchaseItem);
        Assertions.assertEquals(purchase.getId(),
                actualPurchaseItem.getPurchase().getId());
        Assertions.assertEquals(actualPurchase.getId(),
                actualPurchaseItem.getPurchase().getId());
    }
}
