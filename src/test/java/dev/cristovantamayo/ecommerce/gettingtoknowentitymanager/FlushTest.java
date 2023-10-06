package dev.cristovantamayo.ecommerce.gettingtoknowentitymanager;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.model.PurchaseStatus;
import org.junit.Assert;
import org.junit.Test;

public class FlushTest extends EntityManagerTest {

    @Test(expected = Exception.class)
    public void callFlush() {
        try {
            entityManager.getTransaction().begin();

            Purchase purchase = entityManager.find(Purchase.class, 1);
            purchase.setStatus(PurchaseStatus.PAID_OUT);

            //entityManager.flush();

            if(purchase.getPaymentCredcard() == null) {
                throw new RuntimeException("Purchase not paid yet.");
            }

            entityManager.getTransaction().commit();

        } catch(Exception ex) {
            entityManager.getTransaction().toString();
            throw ex;
        }
    }

    @Test
    public void autoFlush() {
        try {
            entityManager.getTransaction().begin();

            Purchase purchase = entityManager.find(Purchase.class, 1);
            purchase.setStatus(PurchaseStatus.PAID_OUT);

            // JPQL forces Flush
            Purchase purchasePaid = entityManager
                    .createQuery("select p from Purchase p where p.id = 1", Purchase.class)
                    .getSingleResult();

            Assert.assertEquals(purchase.getStatus(), purchasePaid.getStatus());
            entityManager.getTransaction().commit();
        } catch(Exception ex) {
            entityManager.getTransaction().toString();
            throw ex;
        }
    }

}
