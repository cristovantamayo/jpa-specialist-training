package dev.cristovantamayo.ecommerce.advancedmapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

public class MapsIdTest extends EntityManagerTest {

    @Test
    public void insertPayment() {
        Purchase purchase = entityManager.find(Purchase.class, 1);

        Invoice invoice = new Invoice();
        invoice.setPurchase(purchase);
        invoice.setIssueDate(new Date());
        invoice.setXml("<xml/>".getBytes());

        entityManager.getTransaction().begin();
        entityManager.persist(invoice);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Invoice actualInvoice = entityManager.find(Invoice.class, invoice.getId());
        Assertions.assertNotNull(actualInvoice);
        Assertions.assertEquals(purchase.getId(), actualInvoice.getId());
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
        entityManager.persist(purchase);
        entityManager.persist(itemPurchase);
        entityManager.getTransaction().commit();

        entityManager.clear();

        PurchaseItem actualPurchaseItem = entityManager.find(
        PurchaseItem.class, new PurchaseItemId(purchase.getId(), product.getId()));
        Assertions.assertNotNull(actualPurchaseItem);
    }
}
