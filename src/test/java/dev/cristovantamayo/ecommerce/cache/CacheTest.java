package dev.cristovantamayo.ecommerce.cache;

import dev.cristovantamayo.ecommerce.model.Purchase;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;

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
    public void ChechIfIsInL2Cache () {
        Cache cache = entityManagerFactory.getCache();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        System.out.println("\n--------------------------------------------");
        System.out.println("Search from Instance 1");
        entityManager1
                .createQuery("select p from Purchase p", Purchase.class)
                .getResultList();

        Assertions.assertTrue(cache.contains(Purchase.class, 1));
        Assertions.assertTrue(cache.contains(Purchase.class, 2));
    }

    @Test
    public void RemoveFromL2Cache () {
        Cache cache = entityManagerFactory.getCache();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("\n--------------------------------------------");
        System.out.println("Search from Instance 1");
        entityManager1
                .createQuery("select p from Purchase p", Purchase.class)
                .getResultList();

        System.out.println("\n--------------------------------------------");
        System.out.println("Remove from L2 Cache");
        /** cache.evict(Purchase.class, 1); // --> remove a Entity reference */
        /** cache.evict(Purchase.class); // --> remove all Entity reference */
        cache.evictAll(); // clear L2 Cache

        System.out.println("\n--------------------------------------------");
        System.out.println("Search from Instance 2");
        entityManager2.find(Purchase.class, 1);
        entityManager2.find(Purchase.class, 2);
    }

    @Test
    public void addPurchasesToCache(){
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("\n--------------------------------------------");
        System.out.println("Search from Instance 1");
        entityManager1
                .createQuery("select p from Purchase p", Purchase.class)
                .getResultList();
        /** In this case the Entities from result Query will be added to L2 Cache */

        System.out.println("\n--------------------------------------------");
        System.out.println("Search from Purchase 2");
        entityManager2.find(Purchase.class, 1);

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
