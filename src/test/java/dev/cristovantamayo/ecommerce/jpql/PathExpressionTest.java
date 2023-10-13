package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class PathExpressionTest extends EntityManagerTest {

    @Test
    public void searchPurchaseWithASpecificProduct() {
        final String jpql = "select p from Purchase p " +
                "join p.purchaseItems i WHERE i.product.id = 1";

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(jpql, Purchase.class);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
        purchases.forEach(p ->
                p.getPurchaseItems().forEach(i ->
                        System.out.println(i.getProduct().getName())));
    }

    @Test
    public void usePathExpressions() {

        final String jpql = "select p.client.name from Purchase p"; // in where clause, to use join that guarantee inner join at build time.

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
    }

}
