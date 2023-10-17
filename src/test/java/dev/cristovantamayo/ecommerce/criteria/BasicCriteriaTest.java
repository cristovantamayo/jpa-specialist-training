package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.EntityManagerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BasicCriteriaTest extends EntityManagerTest {

    @Test
    public void searchById() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        /** Default root */
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        /** final String jpql = "select p from Purchase p where p.id = 1"; */
        TypedQuery<Purchase> typedQuery = entityManager
                /**.createQuery(jpql, Purchase.class)*/
                .createQuery(criteriaQuery);

        Purchase purchase = typedQuery.getSingleResult();
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
    }
}
