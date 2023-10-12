package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Product;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

public class JoinTest extends EntityManagerTest {

    @Test
    public void doJoin() {

        final String jpqlJoin = "select p From Purchase p join p.payment pg";
        TypedQuery<Purchase> typedQueryJoin = entityManager.createQuery(jpqlJoin, Purchase.class);
        List<Purchase> purchasesJoin = typedQueryJoin.getResultList();
        Assertions.assertTrue(purchasesJoin.size() == 1);

        final String jpql = "select p From Purchase p";
        TypedQuery<Purchase> typedQuery = entityManager.createQuery(jpql, Purchase.class);
        List<Purchase> purchases = typedQuery.getResultList();
        // Assertions.assertTrue(purchases.size() == 1); // without join fails

        /** Advantage */
        final String jpqlAdv= "select p, i from Purchase p join p.purchaseItems i";
        TypedQuery<Object[]> typedQueryAdv = entityManager.createQuery(jpqlAdv, Object[].class);
        List<Object[]> purchasesAdv = typedQueryAdv.getResultList();
        System.out.println("results: " + purchasesAdv.size());
        Assertions.assertTrue(purchasesAdv.size() == 3);

        /** Advantage 2 */
        final String jpqlAdv2= "select pro from Purchase p join p.purchaseItems i join i.product pro";
        TypedQuery<Product> typedQueryAdv2 = entityManager.createQuery(jpqlAdv2, Product.class);
        List<Product> products = typedQueryAdv2.getResultList();
        products.forEach(p -> System.out.println(p.getName()));
        Assertions.assertTrue(products.size() == 3);

    }

    @Test
    public void doInnerOuterJoin() {
        /** Inner Join vs Inner Outer Join: the second brings all purchases */
        final String jpqlJoin = "select p From Purchase p left join p.payment pg";
        TypedQuery<Purchase> typedQueryJoin = entityManager.createQuery(jpqlJoin, Purchase.class);
        List<Purchase> purchasesJoin = typedQueryJoin.getResultList();
        Assertions.assertTrue(purchasesJoin.size() == 2);

        final String jpql = "select p From Purchase p left join p.payment pg on pg.paymentStatus = 'IN_PROCESS'";
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
    }
}
