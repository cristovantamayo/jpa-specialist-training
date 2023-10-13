package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.TimeZone;

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
}
