package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.Product;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

import static java.lang.String.format;

public class SubQueriesTest extends EntityManagerTest {

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
