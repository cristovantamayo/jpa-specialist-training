package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Category;
import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class HybridApproachTest extends EntityManagerTest {

    @BeforeAll
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");

        EntityManager entity = entityManagerFactory.createEntityManager();

        final String jpql = "select c from Category c";
        TypedQuery<Category> typedQuery = entity.createQuery(jpql, Category.class);

        entityManagerFactory.addNamedQuery("Category.list", typedQuery);
    }
    @Test
    public void useHybridApproach() {
        TypedQuery<Category> typedQuery = entityManager.createNamedQuery("Category.list", Category.class);
        List<Category> categories = typedQuery.getResultList();
        Assertions.assertFalse(categories.isEmpty());
    }
}
