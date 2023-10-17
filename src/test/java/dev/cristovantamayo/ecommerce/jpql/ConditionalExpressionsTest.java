package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.Product;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public class ConditionalExpressionsTest extends EntityManagerTest {

    @Test
    public void useConditionalExpressionIN() {
        List<Integer> params = Arrays.asList(1, 2, 4);
        final String jpql ="select p from Purchase p where p.id in (:ids)";

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(jpql, Purchase.class);
        typedQuery.setParameter("ids", params);
        List<Purchase> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());



        Client client1 = new Client();
        client1.setId(1);

        Client client2 = entityManager.find(Client.class, 2);
        List<Client> clients = Arrays.asList(client1, client2);

        final String jpqlClient = "select p from Purchase p where p.client in (:clients)";

        TypedQuery<Purchase> typedQueryClient = entityManager.createQuery(jpqlClient, Purchase.class);
        typedQueryClient.setParameter("clients", clients);
        List<Purchase> listClient = typedQueryClient.getResultList();
        Assertions.assertFalse(listClient.isEmpty());
    }

    @Test
    public void useConditionalExpressionCase() {
        final String jpql2 = "select p.id, " +
                "case p.status " +
                "   when 'PAID_OUT' then  'Is Paid' " +
                "   when 'CANCELED' then 'Was Canceled' " +
                "   else 'Is Waiting' " +
                "end " +
                "from Purchase p";

        final String jpql = "select p.id, " +
                "case type(p.payment) " +
                "   when 'CredCard' then 'Paid with CredCard' " +
                "   when 'Ticket' then 'Paid with Ticket' " +
                "   else 'Not Paid yet' " +
                "end " +
                "from Purchase p";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
        list.forEach(arr -> System.out.println(format("%s, %s", arr[0], arr[1])));
    }

    @Test
    public void useConditionalExpressionLike() {
        final String jpql = "select c from Client c where c.name like concat('%', :name, '%')";
        TypedQuery<Client> typedQuery = entityManager.createQuery(jpql, Client.class);
        typedQuery.setParameter("name", "Fern");
        List<Client> clients = typedQuery.getResultList();
        clients.forEach(c -> System.out.println(c.getName()));
        Assertions.assertFalse(clients.isEmpty());
    }

    @Test
    public void useIsNull() {
        String jpql ="select p from Product p where p.photo is null";
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void useIsEmpty() {
        String jpql ="select p from Product p WHERE p.attributes is empty";
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void useMajorAndMinor() {
        String jpqlMajor ="select p from Product p WHERE p.price > :minPrice";
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpqlMajor, Object[].class);
        typedQuery.setParameter("minPrice", new BigDecimal(499));
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());

        String jpqlMinor ="select p from Product p WHERE p.price < :maxPrice";
        TypedQuery<Object[]> typedQueryMinor = entityManager.createQuery(jpqlMinor, Object[].class);
        typedQueryMinor.setParameter("maxPrice", new BigDecimal(1000));
        List<Object[]> listMinor = typedQueryMinor.getResultList();
        Assertions.assertFalse(listMinor.isEmpty());
    }

    @Test
    public void useMajorAndMinorWithDates() {
        final String jpql ="select p from Purchase p where p.purchaseDate >= :initial and p.purchaseDate <= :final";
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        typedQuery.setParameter("initial", LocalDateTime.now().minusDays(2));
        typedQuery.setParameter("final", LocalDateTime.now().plusDays(1));
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void useBetween() {
        final String jpql ="select p from Purchase p " +
                "where p.purchaseDate between :initial and :final";
        TypedQuery<Purchase> typedQuery = entityManager.createQuery(jpql, Purchase.class);
        typedQuery.setParameter("initial", LocalDateTime.now().minusDays(2));
        typedQuery.setParameter("final", LocalDateTime.now().plusDays(1));
        List<Purchase> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());

        final String jpqlQtd = "select p from Product p " +
                "where p.price between :initial and :final";
        TypedQuery<Product> typedQueryQtd = entityManager.createQuery(jpqlQtd, Product.class);
        typedQueryQtd.setParameter("initial", new BigDecimal(499));
        typedQueryQtd.setParameter("final", new BigDecimal(1400));
        List<Product> listQtd = typedQueryQtd.getResultList();
        Assertions.assertFalse(listQtd.isEmpty());
    }
}