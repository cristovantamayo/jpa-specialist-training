package dev.cristovantamayo.ecommerce.concurrence;

import dev.cristovantamayo.ecommerce.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;

public class OptimisticLockTest {

    protected static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");
    }

    @AfterAll
    public static void tearDownAfterClass() {
        entityManagerFactory.close();
    }

    private static void log(Object obj, Object... args) {
        System.out.println(
                String.format("[LOG " + System.currentTimeMillis() + "] " + obj, args)
        );
    }

    private static void waiting(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {}
    }

    @Test
    public void useOptimisticLock() {
        Runnable runnable1 = () -> {

            EntityManager entityManager1 = entityManagerFactory.createEntityManager();
            entityManager1.getTransaction().begin();

            log("Runnable 01 will charge the product 1.");
            Product product = entityManager1.find(Product.class, 1);

            log("Runnable 01 will wait during 1 seconds.");
            waiting(4);

            log("Runnable 01 will alter the product.");
            product.setDescription("Detailed Description.");

            log("Runnable 01 will confirm the transaction. " + Instant.now());
            entityManager1.getTransaction().commit();
            entityManager1.close();
        };

        Runnable runnable2 = () -> {

            EntityManager entityManager2 = entityManagerFactory.createEntityManager();
            entityManager2.getTransaction().begin();

            log("Runnable 02 will charge the product 1.");
            Product product = entityManager2.find(Product.class, 1);

            log("Runnable 02 will wait during 3  second.");
            waiting(1);

            log("Runnable 02 will alter the product.");
            product.setDescription("Very Cool Description!");

            log("Runnable 02 will confirm the transaction. " +  Instant.now());
            entityManager2.getTransaction().commit();
            entityManager2.close();
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        EntityManager entityManager3 = entityManagerFactory.createEntityManager();
        Product product = entityManager3.find(Product.class, 1);
        entityManager3.close();

        Assertions.assertEquals("Very Cool Description!", product.getDescription());

        log("Ending the test method.");
    }

}
