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
    public void ehcache () {
        Cache cache = entityManagerFactory.getCache();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        log("\n--------------------------------------------");
        log("Search from Instance 1");
        entityManager1
                .createQuery("select p from Purchase p", Purchase.class)
                .getResultList();
        log("---");

        waiting(1);
        Assertions.assertTrue(cache.contains(Purchase.class, 1));

        waiting(6);
        Assertions.assertFalse(cache.contains(Purchase.class, 1));
    }

    private static void waiting(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {}
    }

    private static void log(String x) {
        System.out.println(format("[LOG + %s]", x));
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

        log("\n--------------------------------------------");
        log("Retrieve All purchases instance 1");
        entityManager1
                .createQuery("select p from Purchase p", Purchase.class)
                // .setHint("jakarta.persistence.cache.storeMode", CacheStoreMode.BYPASS)
                .getResultList();

        log("\n--------------------------------------------");
        log("Search for Purchase 2 from Instance 2");
        Map<String, Object> properties = new HashMap<>();
        // properties.put("jakarta.persistence.cache.storeMode", CacheStoreMode.BYPASS);
        // properties.put("jakarta.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        entityManager2.find(Purchase.class, 2, properties);

        log("\n--------------------------------------------");
        log("Retrieve All purchases from Instance 3  (again)");
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

        log("\n--------------------------------------------");
        log("Search from Instance 1");
        entityManager1
                .createQuery("select p from Purchase p", Purchase.class)
                .getResultList();

        Assertions.assertTrue(cache.contains(Purchase.class, 1));
    }

    @Test
    public void checkIfIsInL2Cache () {
        Cache cache = entityManagerFactory.getCache();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        log("\n--------------------------------------------");
        log("Search from Instance 1");
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

        log("\n--------------------------------------------");
        log("Search from Instance 1");
        entityManager1
                .createQuery("select p from Purchase p", Purchase.class)
                .getResultList();

        log("\n--------------------------------------------");
        log("Remove from L2 Cache");
        /** cache.evict(Purchase.class, 1); // --> remove a Entity reference */
        /** cache.evict(Purchase.class); // --> remove all Entity reference */
        cache.evictAll(); // clear L2 Cache

        log("\n--------------------------------------------");
        log("Search from Instance 2");
        entityManager2.find(Purchase.class, 1);
        entityManager2.find(Purchase.class, 2);
    }

    @Test
    public void addPurchasesToCache(){
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        log("\n--------------------------------------------");
        log("Search from Instance 1");
        entityManager1
                .createQuery("select p from Purchase p", Purchase.class)
                .getResultList();
        /** In this case the Entities from result Query will be added to L2 Cache */

        log("\n--------------------------------------------");
        log("Search from Purchase 2");
        entityManager2.find(Purchase.class, 1);

    }

    @Test
    public void searchFromCache(){
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        log("Search from Instance 1");
        entityManager1.find(Purchase.class, 1);

        log("\n--------------------------------------------");
        log("Search from Instance 2");
        entityManager2.find(Purchase.class, 1);
    }
}
