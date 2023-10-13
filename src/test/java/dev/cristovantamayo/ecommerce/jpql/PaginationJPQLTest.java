package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Category;
import dev.cristovantamayo.ecommerce.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static java.lang.String.format;

public class PaginationJPQLTest extends EntityManagerTest {

    @Test
    public void paginateResults() {
        final String jpql = "select c from Category c order by c.name";
        TypedQuery<Category> typedQuery = entityManager.createQuery(jpql, Category.class);

        for(int i=1; i<=5; i++) {
            Integer page = i;
            Integer perPage = 2;
            Integer toPage = perPage + page - 1;

            typedQuery.setFirstResult(toPage);
            typedQuery.setMaxResults(perPage);

            List<Category> categories = typedQuery.getResultList();
            Assertions.assertFalse(categories.isEmpty());

            System.out.println("page: " + page);
            categories.forEach(c -> System.out.println(format("%s, %s", c.getId(), c.getName())));
            System.out.println("---------------");
            System.out.println("");
        }
    }
}
