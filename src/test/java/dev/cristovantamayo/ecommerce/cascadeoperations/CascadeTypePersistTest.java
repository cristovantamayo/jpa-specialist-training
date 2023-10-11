package dev.cristovantamayo.ecommerce.cascadeoperations;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class CascadeTypePersistTest extends EntityManagerTest {

    // @Test
    public void persistirPurchaseComItens() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setClient(client);
        purchase.setTotal(product.getPrice());
        purchase.setStatus(PurchaseStatus.WAITING);

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setId(new PurchaseItemId());
        purchaseItem.setPurchase(purchase);
        purchaseItem.setProduct(product);
        purchaseItem.setQuantity(1);
        purchaseItem.setProductPrice(product.getPrice());

        purchase.setPurchaseItems(Arrays.asList(purchaseItem)); // CascadeType.PERSIST

        entityManager.getTransaction().begin();
        entityManager.persist(purchase);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assertions.assertNotNull(actualPurchase);
        Assertions.assertFalse(actualPurchase.getPurchaseItems().isEmpty());

    }

    @Test
    public void persistirPurchaseItemComPurchase() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setClient(client);
        purchase.setTotal(product.getPrice());
        purchase.setStatus(PurchaseStatus.WAITING);

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setId(new PurchaseItemId());
        purchaseItem.setPurchase(purchase);// Não é necessário CascadeType.PERSIST porque possui @MapsId.
        purchaseItem.setProduct(product);
        purchaseItem.setQuantity(1);
        purchaseItem.setProductPrice(product.getPrice());

        entityManager.getTransaction().begin();
        entityManager.persist(purchaseItem);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Purchase actualPurchase = entityManager.find(Purchase.class, purchase.getId());
        Assertions.assertNotNull(actualPurchase);
    }

    // @Test
    public void persistirPurchaseComClient() {
        Client client = new Client();
        client.setBirthDate(LocalDate.of(1980, 1, 1));
        client.setGender(ClientGender.MAN);
        client.setName("José Carlos");
        client.setCpf("01234567890");

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setClient(client); // CascadeType.PERSIST
        purchase.setTotal(BigDecimal.ZERO);
        purchase.setStatus(PurchaseStatus.WAITING);

        entityManager.getTransaction().begin();
        entityManager.persist(purchase);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client actualClient = entityManager.find(Client.class, client.getId());
        Assertions.assertNotNull(actualClient);
    }
}
