package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class PathExpressionTest extends EntityManagerTest {

    @Test
    public void usePathExpressions() {

        final String jpql = "select p.client.name from Purchase p"; // in where clause, to use join that guarantee inner join at build time.

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
    }

}
