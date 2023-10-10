package dev.cristovantamayo.ecommerce.gettingtoknowentitymanager;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.model.PurchaseStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionManagementTest extends EntityManagerTest {

    @Test
    public void openCloseCancelTransaction() {
        Assertions.assertThrows(Exception.class, () -> expectedBusinessException());
    }

    private void expectedBusinessException() {

        try {
            entityManager.getTransaction().begin();
            businessTrade();
            entityManager.getTransaction().commit();
        } catch(Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        }
    }

    private void businessTrade() {
        Purchase purchase = entityManager.find(Purchase.class, 1);
        purchase.setStatus(PurchaseStatus.PAID_OUT);

        if(purchase.getPayment() == null) {
            throw new RuntimeException("Purchase not paid yet");
        }
    }
}
