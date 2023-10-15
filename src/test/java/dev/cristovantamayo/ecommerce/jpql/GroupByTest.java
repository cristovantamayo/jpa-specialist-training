package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static java.lang.String.format;

public class GroupByTest extends EntityManagerTest {

    @Test
    public void groupingWithHaving() {
        // Total of sales that categories most sales
        final String jpql = "select cat.name, sum(i.productPrice) " +
                "from PurchaseItem i " +
                "join i.product pro " +
                "join pro.categories cat " +
                "group by cat.id " +
                "having sum(i.productPrice) > 3000"; /** sum --> total, avg --> the average, others */

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> objects = typedQuery.getResultList();

        Assertions.assertTrue(objects.get(0).length == 2);
        objects.forEach(obj -> System.out.println(format("%s, %s", obj[0], obj[1])));
    }

    @Test
    public void groupResults() {
        /**
         * Quantity of product by category
         * Total of sales per Month
         * Total of sales by category
         */
        // Total of sales per Month
        final String jpql1 = "select concat(year(p.purchaseDate), '/', function('monthname', p.purchaseDate)), " +
                "sum(p.total) " +
                "from Purchase p " +
                "where year(p.purchaseDate) = year(current_date) " +
                "group by year(p.purchaseDate), month(p.purchaseDate)";
        // total per product
        final String jpql2 = "select c.name, sum(i.productPrice) from PurchaseItem i " +
                "join i.product pro " +
                "join pro.categories c " +
                "group by c.id";
        // total per Client last 3 months
        final String jpql3 = "select c.name, concat('Total of Sales: ', count(i)) from PurchaseItem i " +
                "join i.purchase p " +
                "join p.client c " +
                "where year(p.purchaseDate) = year(current_date) and month(p.purchaseDate) >= (month(current_date)-3)" +
                "group by c.name";
        // total per category per month
        final String jpql = "select " +
                " concat(year(p.purchaseDate), '/', month(p.purchaseDate), '/', day(p.purchaseDate)), " +
                " concat(c.name, ': ', sum(ip.productPrice)) " +
                " from PurchaseItem ip join ip.purchase p join ip.product pro join pro.categories c " +
                " group by concat(year(p.purchaseDate), '/', month(p.purchaseDate), '/', day(p.purchaseDate)), c.id " +
                " order by concat(year(p.purchaseDate), '/', month(p.purchaseDate), '/', day(p.purchaseDate)), c.name ";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql3, Object[].class);
        List<Object[]> objects = typedQuery.getResultList();

        Assertions.assertTrue(objects.get(0).length == 2);
        objects.forEach(obj -> System.out.println(format("%s, %s", obj[0], obj[1])));
    }
}
