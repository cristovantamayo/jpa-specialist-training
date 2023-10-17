package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class JoinCriteriaTest extends EntityManagerTest {

    @Test
    public void doCriteriaJoinFetchAsJoin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);
        //root.fetch("invoice", JoinType.LEFT);
        //root.fetch("payment", JoinType.LEFT);
        Join<Purchase, Client> joinClient = root.join("client");
               // (Join<Purchase, Client>) root.<Purchase, Client>fetch("client");


        criteriaQuery.select(joinClient);

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);
        Client client = typedQuery.getSingleResult();
        Assertions.assertNotNull(client);
        System.out.println(client.getName());
    }

    @Test
    public void doCriteriaJoinFetch() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);
        root.fetch("client");
        root.fetch("invoice", JoinType.LEFT);
        root.fetch("payment", JoinType.LEFT);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        Purchase purchase = typedQuery.getSingleResult();
        Assertions.assertNotNull(purchase);
    }

    @Test
    public void doCriteriaLeftOuterJoin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);
        Join<Purchase, Payment> joinPayment = root.join("payment", JoinType.LEFT);

        criteriaQuery.select(root);

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertTrue(purchases.size() == 5);
    }

    @Test
    public void doCriteriaJoinOn() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);
        Join<Purchase, Payment> joinPayment = root.join("payment");
        joinPayment.on(criteriaBuilder
                .equal(joinPayment.get("paymentStatus"), PaymentStatus.IN_PROCESS));

        criteriaQuery.select(root);

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertTrue(purchases.size() == 2);
    }

    @Test
    public void doCriteriaJoin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);
        Join<Purchase, Payment> join = root.join("payment");
        // Join<Purchase, PurchaseItem> joinItems = root.join("purchaseItems");
        // Join<PurchaseItem, Product> joinPurchaseItem = joinItems.join("product");

        criteriaQuery.select(root);

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertTrue(purchases.size() == 4);



        CriteriaQuery<Payment> criteriaQuery2 = criteriaBuilder.createQuery(Payment.class);
        Root<Purchase> root2 = criteriaQuery2.from(Purchase.class);
        Join<Purchase, Payment> joinPayment = root2.join("payment");

        criteriaQuery2.select(joinPayment);
        criteriaQuery2.where(criteriaBuilder
                .equal(joinPayment.get("paymentStatus"), PaymentStatus.IN_PROCESS));

        TypedQuery<Payment> typedQuery2 = entityManager.createQuery(criteriaQuery2);
        List<Payment> payments = typedQuery2.getResultList();
        Assertions.assertTrue(payments.size() == 2);
    }
}
