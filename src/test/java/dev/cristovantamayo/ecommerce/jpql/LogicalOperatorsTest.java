package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Product;
import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.model.PurchaseStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

public class LogicalOperatorsTest extends EntityManagerTest {

    @Test
    public void useOperator() {
        final String jpql = "select p from Purchase p " +
                "where p.total > :minTotal and p.status = :status " +
                "and p.client.id = :clientId";
        TypedQuery<Purchase> typedQuery = entityManager.createQuery(jpql, Purchase.class);
        typedQuery.setParameter("minTotal", new BigDecimal(100));
        typedQuery.setParameter("status", PurchaseStatus.WAITING);
        typedQuery.setParameter("clientId", 1);
        List<Purchase> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());

        final String jpql2 = "select p from Purchase p " +
                "where (p.status = :status or p.status = :status2) and p.total > :minTotal";
        TypedQuery<Purchase> typedQuery2 = entityManager.createQuery(jpql2, Purchase.class);
        typedQuery2.setParameter("minTotal", new BigDecimal(100));
        typedQuery2.setParameter("status", PurchaseStatus.WAITING);
        typedQuery2.setParameter("status2", PurchaseStatus.PAID_OUT);
        List<Purchase> list2 = typedQuery2.getResultList();
        Assertions.assertFalse(list2.isEmpty());
    }
}
