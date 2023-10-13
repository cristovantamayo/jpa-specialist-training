package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static java.lang.String.format;

public class StringFunctionsTest extends EntityManagerTest {

    // concat, length, locate, substring, lower, upper, trim
    @Test
    public void applyFunction() {
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
}
