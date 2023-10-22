package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.Product;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

import static java.lang.String.format;

public class SubQueriesTest extends EntityManagerTest {

    @Test
    public void searchWithINExercise() {
        /** Purchases that contain category 2 products */
        String jpql = "select p from Purchase p where p.id IN (" +
                "         select i.purchase.id from PurchaseItem i " +
                "               join i.product pro " +
                "               join pro.categories c " +
                "                   where c.id = 2" +
                "      )";

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(jpql, Purchase.class);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
        purchases.forEach(p -> System.out.println(format("PurchaseId: %s", p.getId())));
    }

    @Test
    public void searchSubQueriesAllExercise() {

        /** All products that always been sold at the same price */
        final String jpql = "select pro from PurchaseItem i " +
                "join i.product pro " +
                "where i.productPrice = All (" +
                "   select i2.productPrice FROM PurchaseItem i2 " +
                "       where i2.product = pro " +
                "           and i2.purchase <> i.purchase" +
                ")";

        TypedQuery<Product> typedQuery1 = entityManager.createQuery(jpql, Product.class);
        List<Product> products = typedQuery1.getResultList();
        Assertions.assertFalse(products.isEmpty());
        products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
    }

    @Test
    public void searchSubQueriesWithAny() {

        /** All products that always been sold at the current price. */
        final String jpql2 = "select p from Product p " +
                "where p.price = ANY (" +
                "   select productPrice from PurchaseItem where product = p" +
                ")";

        /** All products that were not sold at the current price. */
        final String jpql = "select p from Product p " +
                "where p.price <> ANY ( " +
                "  select productPrice from PurchaseItem where product = p" +
                ")";
        /** Can be substituted by SOME */
        final String jpql3 = "select p from Product p " +
                "where p.price <> SOME ( " +
                "  select productPrice from PurchaseItem where product = p" +
                ")";

        TypedQuery<Product> typedQuery1 = entityManager.createQuery(jpql, Product.class);
        List<Product> products = typedQuery1.getResultList();
        Assertions.assertFalse(products.isEmpty());
        products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
    }
    @Test
    public void searchSubQueriesWithAll() {

        /** All products that have been sold at least once at the current price. */
        final String jpql2 = "select p from Product p ";

        /** All products that were not sold at the current price. */
        final String jpql = "select p from Product p " +
                "where p.price > ALL ( " +
                "  select productPrice from PurchaseItem where product = p" +
                ")";

        final String jpql_like = "select p from Product p " +
                "where p.price > ( " +
                "  select max(productPrice) from PurchaseItem where product = p" +
                ")";

        TypedQuery<Product> typedQuery1 = entityManager.createQuery(jpql, Product.class);
        List<Product> products = typedQuery1.getResultList();
        Assertions.assertFalse(products.isEmpty());
        products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
    }

    @Test
    public void searchSubQueriesWithExists2() {
        final String jpql = "select p from Product p " +
                "where exists (" +
                "   select 1 from PurchaseItem i2 " +
                "       join i2.product p2 " +
                "   where p.id = p2.id and p2.price > i2.productPrice" +
                ")";

        TypedQuery<Product> typedQuery1 = entityManager.createQuery(jpql, Product.class);
        List<Product> products = typedQuery1.getResultList();
        Assertions.assertFalse(products.isEmpty());
        products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
    }

    @Test
    public void searchExerciseSubQuery() {
        final String jpql4 = "select c from Client c " +
                "where exists (" +
                "   select 1 from Purchase p " +
                "       join p.purchaseItems i" +
                "       where p.client = c" +
                "           and size(i) > 1 " +
                ")";

        TypedQuery<Client> typedQuery = entityManager.createQuery(jpql4, Client.class);
        List<Client> clients = typedQuery.getResultList();
        Assertions.assertFalse(clients.isEmpty());
        clients.forEach(c -> System.out.println(format("CLIENT_ID: %s, NAME: %s", c.getId(), c.getName())));
    }

    @Test
    public void searchExerciseSubQueriesWithIN() {
        final String jpql = "select p from Purchase p " +
                "where p.id in (" +
                "   select i2.purchase.id from PurchaseItem i2 " +
                "       join i2.product p2 " +
                "       join p2.categories c2 " +
                "   where :categoryId = c2.id" +
                ")";

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(jpql, Purchase.class);
        typedQuery.setParameter("categoryId", 8);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
        purchases.forEach(c -> System.out.println(format("CLIENT_ID: %s, NAME: %s", c.getId(), c.getClient().getName())));
    }

    @Test
    public void searchSubQueriesWithExists() {
        final String jpql = "select p from Product p " +
                "where exists (" +
                "   select 1 from PurchaseItem i2 " +
                "       join i2.product p2 " +
                "   where p2 = p" +
                ")";

        TypedQuery<Product> typedQuery1 = entityManager.createQuery(jpql, Product.class);
        List<Product> products = typedQuery1.getResultList();
        Assertions.assertFalse(products.isEmpty());
        products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
    }

    @Test
    public void searchSubQueriesWithIN() {
        final String jpql = "select p from Purchase p " +
                "where p.id in (" +
                "   select p2.id from PurchaseItem i2 " +
                "       join i2.purchase p2 " +
                "       join i2.product pro2 " +
                "   where pro2.price > :minimum" +
                ")";

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(jpql, Purchase.class);
        typedQuery.setParameter("minimum", new BigDecimal(100));
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
        purchases.forEach(c -> System.out.println(format("CLIENT_ID: %s, NAME: %s", c.getId(), c.getClient().getName())));
    }

    @Test
    public void searchSubQueries() {

        // more expensive product
        final String jpql1 = "select p from Product p " +
                "where  p.price = (" +
                "   select max(price) from Product" +
                ")";

        TypedQuery<Product> typedQuery1 = entityManager.createQuery(jpql1, Product.class);
        List<Product> products = typedQuery1.getResultList();
        Assertions.assertFalse(products.isEmpty());
        products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));

        // All products that are upside de sales average
        final String jpql2 = "select p from Purchase p " +
                "where p.total > (" +
                "   select avg(total) from Purchase" +
                ")";

        TypedQuery<Purchase> typedQuery2 = entityManager.createQuery(jpql2, Purchase.class);
        List<Purchase> purchase = typedQuery2.getResultList();
        Assertions.assertFalse(purchase.isEmpty());
        purchase.forEach(p -> System.out.println(format("ID: %s, TOTAL: %s", p.getId(), p.getTotal())));

        // Best Clients
        final String jpql3 = "select c from Client c " +
                "where :minimum < (" +
                "   select sum(p.total) from c.purchases p" +
                ")";

        final String jpql4 = "select c from Client c " +
                "where :minimum < (" +
                "   select sum(p.total) from Purchase p " +
                "       where p.client = c" +
                ")";

        TypedQuery<Client> typedQuery = entityManager.createQuery(jpql4, Client.class);
        typedQuery.setParameter("minimum", new BigDecimal(500));
        List<Client> clients = typedQuery.getResultList();
        Assertions.assertFalse(clients.isEmpty());
        clients.forEach(c -> System.out.println(format("CLIENT_ID: %s, NAME: %s", c.getId(), c.getName())));

    }
}
