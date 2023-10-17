package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Invoice;
import dev.cristovantamayo.ecommerce.model.PaymentStatus;
import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.model.PurchaseStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.persistence.TemporalType;
import jakarta.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class PathExpressionTest extends EntityManagerTest {

    @Test
    public void passingParameters() {
        final String jpql = "select p from Purchase p " +
                "join p.payment pg " +
                "where p.id = ?1 and pg.paymentStatus = ?2";

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(jpql, Purchase.class);
        typedQuery.setParameter(1, 2);
        typedQuery.setParameter(2, PaymentStatus.IN_PROCESS);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertTrue(purchases.size() == 1);

        final String jpqlNamed = "select p from Purchase p " +
                "join p.payment pg " +
                "where p.id = :purchaseId and pg.paymentStatus = :status";

        TypedQuery<Purchase> typedQueryNamed = entityManager.createQuery(jpqlNamed, Purchase.class);
        typedQueryNamed.setParameter("purchaseId", 2);
        typedQueryNamed.setParameter("status", PaymentStatus.IN_PROCESS);
        List<Purchase> purchasesNamed = typedQueryNamed.getResultList();
        Assertions.assertTrue(purchasesNamed.size() == 1);

        final String jpqlMixed = "select p from Purchase p " +
                "join p.payment pg " +
                "where p.id = :purchaseId and pg.paymentStatus = ?2";

        TypedQuery<Purchase> typedQueryMixed = entityManager.createQuery(jpqlMixed, Purchase.class);
        typedQueryMixed.setParameter("purchaseId", 2);
        typedQueryMixed.setParameter(2, PaymentStatus.IN_PROCESS);
        List<Purchase> purchasesMixed = typedQueryMixed.getResultList();
        Assertions.assertTrue(purchasesMixed.size() == 1);

        final String jpqlDate = "select inv from Invoice inv WHERE inv.issueDate <= ?1";

        TypedQuery<Invoice> typedQueryDate = entityManager.createQuery(jpqlDate, Invoice.class);
        typedQueryDate.setParameter(1, new Date(), TemporalType.TIMESTAMP);
        List<Invoice> invoices = typedQueryDate.getResultList();
        Assertions.assertTrue(invoices.size() == 1);
    }

    @Test
    public void searchPurchaseWithASpecificProduct() {
        final String jpql = "select p from Purchase p " +
                "join p.purchaseItems i WHERE i.product.id = 1";

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(jpql, Purchase.class);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
        purchases.forEach(p ->
                p.getPurchaseItems().forEach(i ->
                        System.out.println(i.getProduct().getName())));
    }

    @Test
    public void usePathExpressions() {

        final String jpql = "select p.client.name from Purchase p"; // in where clause, to use join that guarantee inner join at build time.

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
    }

}
