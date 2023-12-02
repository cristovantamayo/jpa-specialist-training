package dev.cristovantamayo.ecommerce.concurrence;

import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.LockModeType;

public class PessimisticLockTest {

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
    public void usePessimisticLockModeTypePessimisticRead() {
        Runnable runnable1 = () -> {
            log("Initializing Runnable 01.");

            String newDescription = "Description detailed. CTM: " + System.currentTimeMillis();

            EntityManager entityManager1 = entityManagerFactory.createEntityManager();
            entityManager1.getTransaction().begin();

            log("Runnable 01 will charges the product 1.");
            Product product = entityManager1.find(
                    Product.class, 1, LockModeType.PESSIMISTIC_READ);

            log("Runnable 01 will change the product.");
            product.setDescription(newDescription);

            log("Runnable 01 will esperar por 3 segundo(s).");
            waiting(3);

            log("Runnable 01 will confirmar a transação.");
            entityManager1.getTransaction().commit();
            entityManager1.close();

            log("Terminating Runnable 01.");
        };

        Runnable runnable2 = () -> {
            log("Initializing Runnable 02.");

            String newDescription = "Very Cool Description! CTM: " + System.currentTimeMillis();

            EntityManager entityManager2 = entityManagerFactory.createEntityManager();
            entityManager2.getTransaction().begin();

            log("Runnable 02 will charges the product 2.");
            Product product = entityManager2.find(
                    Product.class, 1, LockModeType.PESSIMISTIC_READ);

            log("Runnable 02 will change the product.");
            product.setDescription(newDescription);

            log("Runnable 02 will esperar por 1 segundo(s).");
            waiting(1);

            log("Runnable 02 will confirmar a transação.");
            entityManager2.getTransaction().commit();
            entityManager2.close();

            log("Terminating Runnable 02.");
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();

        waiting(1);
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

        Assertions.assertTrue(product.getDescription().startsWith("Very Cool Description!"));

        log("Terminating method of test.");
    }
}