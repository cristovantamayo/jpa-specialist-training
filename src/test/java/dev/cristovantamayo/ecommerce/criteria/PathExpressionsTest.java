package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.String.format;

public class PathExpressionsTest extends EntityManagerTest {

    @Test
    public void ConsultSpecificProductId() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);
        Join<Purchase, PurchaseItem> joinItems = root.join(Purchase_.PURCHASE_ITEMS);

        criteriaQuery.where(
                criteriaBuilder.equal(joinItems.get(PurchaseItem_.PRODUCT).get(Product_.ID), 1)
        );

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
        purchases.forEach(p -> {
            System.out.println(format("purchaseId: %s, Client: %s", p.getId(), p.getClient().getName()));
            p.getPurchaseItems().forEach(i -> System.out.println(format("Product: %s",i.getProduct().getName())));
        });
    }



    @Test
    public void usePathExpressions () {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        criteriaQuery.select(root);

        criteriaQuery.where(
                criteriaBuilder.like(root.get(Purchase_.CLIENT).get(Client_.NAME), "M%"));

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
        purchases.forEach(p -> System.out.println(format("purchaseId: %s, Client: %s", p.getId(), p.getClient().getName())));

    }
}
