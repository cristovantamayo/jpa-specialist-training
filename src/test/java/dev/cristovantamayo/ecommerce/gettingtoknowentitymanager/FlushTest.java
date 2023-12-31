package dev.cristovantamayo.ecommerce.gettingtoknowentitymanager;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.model.PurchaseStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FlushTest extends EntityManagerTest {

    @Test
    public void callFlush() {
        Assertions.assertThrows(Exception.class, () -> expectedBusinessException());
    }


    private void expectedBusinessException() {
        try {
            entityManager.getTransaction().begin();

            Purchase purchase = entityManager.find(Purchase.class, 1);
            purchase.setStatus(PurchaseStatus.PAID_OUT);

            //entityManager.flush();

            if(purchase.getPayment() == null) {
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

            Client client = entityManager.find(Client.class, 2);
            Purchase purchase = entityManager.find(Purchase.class, 1);
            purchase.setStatus(PurchaseStatus.PAID_OUT);
            purchase.setTotal(new BigDecimal(560));
            purchase.setPurchaseDate(LocalDateTime.now());
            purchase.setClient(client);

            entityManager.flush();

            // JPQL forces Flush
            Purchase purchasePaid = entityManager
                    .createQuery("select p from Purchase p where p.id = 1", Purchase.class)
                    .getSingleResult();

            Assertions.assertEquals(purchase.getStatus(), purchasePaid.getStatus());
            entityManager.getTransaction().commit();
        } catch(Exception ex) {
            entityManager.getTransaction().toString();
            throw ex;
        }
    }

}
