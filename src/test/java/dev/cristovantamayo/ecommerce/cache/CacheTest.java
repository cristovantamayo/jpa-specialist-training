package dev.cristovantamayo.ecommerce.cache;

import dev.cristovantamayo.ecommerce.model.Purchase;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.HashMap;
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
    public void controlL2CacheDynamically () {
        /** jakarta.persistence.cache.retrieveMode CacheRetrieveMode;
         * jakarta.persistence.cache.storeMode CacheStoreMode;
         *
         * CacheStoreMode
         *  USE --> All Results will be cached
         *  BYPASS --> Ignores Result ane not add to cache
         *  REFRESH --> like USE, caches all Result, but updates on new results
         *
         * CacheRetrieveMode
         *  USE --> All cached result will be used
         *  BYPASS --> Ignores to use cached result
         */
        Cache cache = entityManagerFactory.getCache();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        System.out.println("\n--------------------------------------------");
        System.out.println("Retrieve All purchases instance 1");
        entityManager1
                .createQuery("select p from Purchase p", Purchase.class)
                // .setHint("jakarta.persistence.cache.storeMode", CacheStoreMode.BYPASS)
                .getResultList();

        System.out.println("\n--------------------------------------------");
        System.out.println("Search for Purchase 2 from Instance 2");
        Map<String, Object> properties = new HashMap<>();
        // properties.put("jakarta.persistence.cache.storeMode", CacheStoreMode.BYPASS);
        // properties.put("jakarta.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        entityManager2.find(Purchase.class, 2, properties);

        System.out.println("\n--------------------------------------------");
        System.out.println("Retrieve All purchases from Instance 3  (again)");
        EntityManager entityManager3 = entityManagerFactory.createEntityManager();
        entityManager3.setProperty("jakarta.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); // Bypass all Consuls from instance 3
        entityManager3
                .createQuery("select p from Purchase p", Purchase.class)
                .setHint("jakarta.persistence.cache.retrieveMode", CacheRetrieveMode.USE) // USE only this consult
                .getResultList();
    }

    @Test
    public void l2CacheOptionsAnalyse () {
        Cache cache = entityManagerFactory.getCache();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        System.out.println("\n--------------------------------------------");
        System.out.println("Search from Instance 1");
        entityManager1
                .createQuery("select p from Purchase p", Purchase.class)
                .getResultList();

        Assertions.assertTrue(cache.contains(Purchase.class, 1));
    }

    @Test
    public void checkIfIsInL2Cache () {
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
    public void removeFromL2Cache () {
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
