package dev.cristovantamayo.ecommerce.cascadeoperations;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class CascadeTypeMergeTest extends EntityManagerTest {

    //@Test
    public void updateProductWithCategory() {

        final BigDecimal PRICE = new BigDecimal(180.00);

        Product product = new Product();
        product.setId(1);
        product.setUpdatedAt(LocalDateTime.now());
        product.setPrice(PRICE);
        product.setName("Kindle");
        product.setDescription("Now with adjustable blue light");

        Category category = new Category();
        category.setId(2);
        category.setName("Tablets");

        product.setCategories(Arrays.asList(category));

        entityManager.getTransaction().begin();
        product = entityManager.merge(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Category actualCategory = entityManager.find(Category.class , product.getCategories().get(0).getId());
        Assertions.assertFalse(actualCategory.getProducts().isEmpty());
        Assertions.assertTrue(actualCategory.getProducts().get(0).getPrice().compareTo(PRICE)==0);

    }

    //@Test
    public void updatePurchaseWithItems() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Purchase purchase = new Purchase();
        purchase.setId(1);
        purchase.setClient(client);
        purchase.setStatus(PurchaseStatus.WAITING);

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setId(new PurchaseItemId());
        purchaseItem.getId().setPurchaseId(purchase.getId());
        purchaseItem.getId().setProductId(product.getId());
        purchaseItem.setPurchase(purchase);
        purchaseItem.setProduct(product);
        purchaseItem.setQuantity(3);
        purchaseItem.setProductPrice(product.getPrice());

        purchase.setPurchaseItems(Arrays.asList(purchaseItem)); // CascadeType.MERGE

        entityManager.getTransaction().begin();
        entityManager.merge(purchase);
        entityManager.getTransaction().commit();

        entityManager.clear();

        PurchaseItem actualPurchaseItem = entityManager.find(PurchaseItem.class, purchaseItem.getId());
        Assertions.assertTrue(actualPurchaseItem.getQuantity().equals(3));
    }

    //@Test
    public void updatePurchaseItemWithPurchase() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Purchase purchase = new Purchase();
        purchase.setId(1);
        purchase.setClient(client);
        purchase.setStatus(PurchaseStatus.PAID_OUT);

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setId(new PurchaseItemId());
        purchaseItem.getId().setPurchaseId(purchase.getId());
        purchaseItem.getId().setProductId(product.getId());
        purchaseItem.setPurchase(purchase); // CascadeType.MERGE
        purchaseItem.setProduct(product);
        purchaseItem.setQuantity(5);
        purchaseItem.setProductPrice(product.getPrice());

        purchase.setPurchaseItems(Arrays.asList(purchaseItem));

        entityManager.getTransaction().begin();
        entityManager.merge(purchaseItem);
        entityManager.getTransaction().commit();

        entityManager.clear();

        PurchaseItem actualPurchaseItem = entityManager.find(PurchaseItem.class, purchaseItem.getId());
        Assertions.assertTrue(PurchaseStatus.PAID_OUT.equals(actualPurchaseItem.getPurchase().getStatus()));
    }
}
