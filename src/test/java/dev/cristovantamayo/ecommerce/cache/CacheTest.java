package dev.cristovantamayo.ecommerce.cache;

import dev.cristovantamayo.ecommerce.model.Purchase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.lang.String.format;

public class CacheTest {

    protected static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");
    }

    @AfterAll
    public static void tearDownAfterClass() {
        entityManagerFactory.close();;
    }

    @Test
    public void searchFromCache(){
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Search from Instance 1");
        entityManager1.find(Purchase.class, 1);

        System.out.println("\n--------------------------------------------");
        System.out.println("Search from Instance 2");
        entityManager2.find(Purchase.class, 1);
    }
}
