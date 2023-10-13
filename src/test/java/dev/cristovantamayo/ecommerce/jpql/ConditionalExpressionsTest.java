package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class ConditionalExpressionsTest extends EntityManagerTest {

    @Test
    public void useConditinalExpressionLike() {
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
}
