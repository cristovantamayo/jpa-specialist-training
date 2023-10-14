package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static java.lang.String.format;

public class GroupByTest extends EntityManagerTest {

    @Test
    public void groupResults() {
        /**
         * Quantity of product by category
         * Total of month sales
         * Total of sales by category
         */
        final String jpql1 = "select concat(year(p.purchaseDate), '/', function('monthname', p.purchaseDate)), " +
                "sum(p.total) " +
                " from Purchase p " +
                " group by year(p.purchaseDate), month(p.purchaseDate)";

        final String jpql = "select c.name, sum(i.productPrice) from PurchaseItem i " +
                "join i.product pro " +
                "join pro.categories c " +
                "group by c.id";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> objects = typedQuery.getResultList();

        Assertions.assertTrue(objects.get(0).length == 2);
        objects.forEach(obj -> System.out.println(format("%s, %s", obj[0], obj[1])));
    }
}
