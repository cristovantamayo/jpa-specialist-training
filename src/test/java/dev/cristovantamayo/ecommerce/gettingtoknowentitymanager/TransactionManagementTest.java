package dev.cristovantamayo.ecommerce.gettingtoknowentitymanager;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.model.PurchaseStatus;
import org.junit.Test;

public class TransactionManagementTest extends EntityManagerTest {

    @Test(expected = Exception.class)
    public void openCloseCancelTransaction() {

        try {
            entityManager.getTransaction().begin();
            businessTrade();
            entityManager.getTransaction().commit();
        } catch(Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        }
    }

    public void businessTrade() {
        Purchase purchase = entityManager.find(Purchase.class, 1);
        purchase.setStatus(PurchaseStatus.PAID_OUT);

        if(purchase.getPaymentCredcard() == null) {
            throw new RuntimeException("Purchase not paid yet");
        }
    }
}
