package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class BasicJPQJTest extends EntityManagerTest {

    @Test
    public void findById() {
        /** Java Persistence Query Language - JPL.
         * JPQL - select p from purchase p join p.purchaseItems pi where pi.product_price > 10
         * SQL - select p.* from purchase p join purchase_item pi ON pi.id_product = p.id where pi.product_price > 10
         */
        /** entityManager.find(Purchase.class, 1)*/

        /** SQL almost equivalence: select p.* from purchase p where p.id = 1 */
        TypedQuery<Purchase> typedQuery = entityManager.createQuery("select p from Purchase p where p.id = 1", Purchase.class);
        /** Need to guarantee unique result */
        Purchase purchase = typedQuery.getSingleResult();

        /** working with all quantity results */
        List<Purchase> purchases = typedQuery.getResultList();

        Assertions.assertNotNull(purchase);
        Assertions.assertFalse(purchases.isEmpty());
    }
}
