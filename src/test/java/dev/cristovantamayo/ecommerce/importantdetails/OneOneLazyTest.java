package dev.cristovantamayo.ecommerce.importantdetails;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OneOneLazyTest extends EntityManagerTest {

    @Test
    public void showProblem() {
        System.out.println("LOOKING FOR AN ORDER:");
        Purchase purchase = entityManager
                .createQuery("select p from Purchase p " +
                        "left join fetch p.payment " +
                        "left join fetch p.client " +
                        "left join fetch p.invoice " +
                        "where p.id = 1", Purchase.class)
                .getSingleResult();
        Assertions.assertNotNull(purchase);

        System.out.println("----------------------------------------------------");

        System.out.println("BUSCANDO UMA LISTA DE PEDIDOS:");
        List<Purchase> lista = entityManager
                .createQuery("select p from Purchase p " +
                        "left join fetch p.payment " +
                        "left join fetch p.client " +
                        "left join fetch p.invoice", Purchase.class)
                .getResultList();
        Assertions.assertFalse(lista.isEmpty());
    }
}
