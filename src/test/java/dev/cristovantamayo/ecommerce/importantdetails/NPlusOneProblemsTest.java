package dev.cristovantamayo.ecommerce.importantdetails;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Purchase;
import jakarta.persistence.EntityGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NPlusOneProblemsTest extends EntityManagerTest {

    @Test
    public void solveWithEntityGraph() {
        EntityGraph<Purchase> purchaseEntityGraph = entityManager
                .createEntityGraph(Purchase.class);
        purchaseEntityGraph.addAttributeNodes("client", "payment", "invoice");
        List<Purchase> list = entityManager
                .createQuery("select p from Purchase p", Purchase.class)
                .setHint("javax.persistence.fetchgraph", purchaseEntityGraph)
                .getResultList();

        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void solveWithFetch() {
        List<Purchase> list = entityManager
                .createQuery("select p from Purchase p " +
                        "join fetch p.client c " +
                        "join fetch p.payment y " +
                        "join fetch p.invoice i", Purchase.class)
                .getResultList();

        Assertions.assertFalse(list.isEmpty());
    }
}
