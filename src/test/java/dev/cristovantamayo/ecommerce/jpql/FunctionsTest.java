package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.persistence.TypedQuery;
import java.util.List;

import static java.lang.String.format;

public class FunctionsTest extends EntityManagerTest {

    // concat, length, locate, substring, lower, upper, trim
    @Test
    public void applyStringFunctions() {
        final String jpql = "select c.name, concat('Category: ', c.name) from Category c";
        final String jpqlLength = "select c.name, length(c.name) from Category c";
        final String jpqlLocate = "select c.name, locate('a', c.name) from Category c";
        final String jpqlSubString= "select c.name, substring(c.name, 1, 3) from Category c";
        final String jpqlLower= "select c.name, lower(c.name) from Category c";
        final String jpqlUpper= "select c.name, upper(c.name) from Category c";
        final String jpqlTrim= "select c.name, trim(c.name) from Category c";


        final String jpqlConditional = "select c.name, length(c.name), upper(substring(c.name, 1, 3)) from Category c " +
                "where length(c.name) > 10";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpqlConditional, Object[].class);
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
        list.forEach(arr -> System.out.println(format("%s, %s, %s", arr[0], arr[1], arr[2]!=null?arr[2]:"")));
    }

    @Test
    public void applyDateFunctions() {
        //TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        final String jpqlConditional = "select current_date, current_time, current_timestamp from Purchase p " +
                "where p.purchaseDate < current_date";

        final String jpqlYEAR = "select year(p.purchaseDate), month(p.purchaseDate), day(p.purchaseDate) from Purchase p";

        final String jpqlHour = "select hour(p.purchaseDate), minute(p.purchaseDate), second(p.purchaseDate) from Purchase p";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpqlHour, Object[].class);
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
        list.forEach(arr -> System.out.println(format("%s | %s | %s", arr[0], arr[1], arr[2]!=null?arr[2]:"")));
    }

    @Test
    public void applyNumberFunctions() {
        /** abs(-10) --> module
         * mod(3,2) --> rest of division
         * sqrt(9) --> square root
          */

        final String jpql = "select abs(-10), mod(3,2), sqrt(9) from Purchase p " +
                "where abs(p.total) > 1000";



        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
        list.forEach(arr -> System.out.println(format("%s | %s | %s", arr[0], arr[1], arr[2]!=null?arr[2]:"")));
    }

    @Test
    public void applyCollectionsFunctions() {
        /**
         */

        final String jpql = "select size(p.purchaseItems) from Purchase p " +
                "where size(p.purchaseItems) > 1";



        TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql, Integer.class);
        List<Integer> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
        list.forEach(size -> System.out.println(size));
    }

    @Test
    public void applyNativeFunctions() {

        final String jpql = "select p from Purchase p " +
                "where function('above_media_billing', p.total) = 1";

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(jpql, Purchase.class);
        List<Purchase> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
        list.forEach(p -> System.out.println(p));

        final String jpql2 = "select function('dayname', p.purchaseDate) from Purchase p " +
                "where function('above_media_billing', p.total) = 1";

        TypedQuery<String> typedQuery2 = entityManager.createQuery(jpql2, String.class);
        List<String> list2 = typedQuery2.getResultList();
        Assertions.assertFalse(list2.isEmpty());
        list2.forEach(p -> System.out.println(p));
    }

    @Test
    public void applyAggregationFunctions() {

        /**
         * avg -> take the average
         * count - count occurrences
         * min - minimum value found
         * max - Maximum value found
         * sum - sum of all values
         */

        final String jpqlAvg = "select avg(p.total) from Purchase p";
        final String jpqlCount = "select count(p) from Purchase p";
        final String jpqlMin = "select min(p.total) from Purchase p";
        final String jpqlMax = "select max(p.total) from Purchase p";
        final String jpqlSum = "select sum(p.total) from Purchase p";

        TypedQuery<Number> typedQuery = entityManager.createQuery(jpqlSum, Number.class);
        List<Number> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
        list.forEach(p -> System.out.println(p));
    }


}
