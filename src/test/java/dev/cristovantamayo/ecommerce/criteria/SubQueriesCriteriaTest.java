package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public class SubQueriesCriteriaTest extends EntityManagerTest {

    @Test
    public void searchWithINExercise() {
        /**
         *
         *      String = "select p from Purchase p where p IN (
         *              select i.purchase from PurchaseItem i
         *                  join i.product pro
         *                      where pro.category.id = 2
         *          )"
         */

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<PurchaseItem> subQueryRoot = subquery.from(PurchaseItem.class);
        Join<PurchaseItem, Product> joinProduct = subQueryRoot.join(PurchaseItem_.product);
        Join<Product, Category> joinCategory = joinProduct.join(Product_.CATEGORIES);
        subquery.select(subQueryRoot.get(PurchaseItem_.id).get(PurchaseItemId_.purchaseId));
        subquery.where(criteriaBuilder.equal(joinCategory.get(Category_.id), 2));

        criteriaQuery.where(root.get(Purchase_.id).in(subquery));

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
        purchases.forEach(p -> System.out.println(format("PurchaseId: %s", p.getId())));
    }

    @Test
    public void searchProductExercise() {
        /** Customers who have already placed more than 2 orders
         *
         *  String jpql = "select c from Client c where exists (
         *          (select count(1) from Purchase p where p.client = c) > 2"
         */

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.select(root);

        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Purchase> subQueryRoot = subquery.from(Purchase.class);
        subquery.select(criteriaBuilder.count(criteriaBuilder.literal(1)));
        subquery.where(criteriaBuilder.equal(subQueryRoot.get(Purchase_.client), root));

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, 2L));

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Client> clients = typedQuery.getResultList();
        Assertions.assertFalse(clients.isEmpty());
        clients.forEach(c -> System.out.println(format("id: %s, name: %s", c.getId(), c.getName())));

    }

    @Test
    public void searchWithExists() {
        /** All products that have already been sold.
         *
         *  String jpql = "select p from Product p where exists " +
         *       " (select 1 from PurchaseItem ip2 join ip2.product p2 where p2 = p)";
         */

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<PurchaseItem> subqueryRoot = subquery.from(PurchaseItem.class);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(PurchaseItem_.product), root));

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void searchWithIN() {
        /** Purchase that Product cost more than 100
        *
        *  String jpql = "select p from Purchase p where p.id in" +
        *        " (select p2.id from PurchaseItem i2 " +
        *        "      join i2.purchase p2 join i2.produto pro2 where pro2.price > 100)";
        */

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<PurchaseItem> subqueryRoot = subquery.from(PurchaseItem.class);
        Join<PurchaseItem, Purchase> subQueryJoinPurchase = subqueryRoot.join(PurchaseItem_.purchase);
        Join<PurchaseItem, Product> subQueryJoinProduct = subqueryRoot.join(PurchaseItem_.product);
        subquery.select(subQueryJoinPurchase.get(Purchase_.id));
        subquery.where(criteriaBuilder.greaterThan(
                subQueryJoinProduct.get(Product_.price), new BigDecimal(1300)));

        criteriaQuery.where(root.get(Purchase_.id).in(subquery));

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Purchase> lista = typedQuery.getResultList();
        Assertions.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void searchSubQueries03() {
        /** Good customers.
        *
        * String jpql = "select c from Client c where " +
        *          " 500 < (select sum(p.total) from Purchase p where p.client = c)";  
        */
        

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Purchase> subqueryRoot = subquery.from(Purchase.class);
        subquery.select(criteriaBuilder.sum(subqueryRoot.get(Purchase_.total)));
        subquery.where(criteriaBuilder.equal(
                root, subqueryRoot.get(Purchase_.client)));

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, new BigDecimal(1300)));

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Client> lista = typedQuery.getResultList();
        Assertions.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println(
                "ID: " + obj.getId() + ", Nome: " + obj.getName()));
    }
    @Test
    public void searchSubQueries02() {
       /** All orders above average sales
       * 
       *  String jpql = "select p from Purchase p where " +
       *          " p.total > (select avg(total) from Purchase)";
       */

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Purchase> subqueryRoot = subquery.from(Purchase.class);
        subquery.select(criteriaBuilder.avg(subqueryRoot.get(Purchase_.total)).as(BigDecimal.class));

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Purchase_.total), subquery));

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Purchase> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println(
                "ID: " + obj.getId() + ", Total: " + obj.getTotal()));
    }

    @Test
    public void searchSubQueries01() {
        /**  The most expensive product or products in the base.
        *
        *  String jpql = "select p from Product p where " +
        *          " p.price = (select max(price) from Product)";
        */

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Product> subqueryRoot = subquery.from(Product.class);
        subquery.select(criteriaBuilder.max(subqueryRoot.get(Product_.PRICE)));

        criteriaQuery.where(criteriaBuilder.equal(root.get(Product_.PRICE), subquery));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println(
                "ID: " + obj.getId() + ", Nome: " + obj.getName() + ", Preço: " + obj.getPrice()));
    }
}
