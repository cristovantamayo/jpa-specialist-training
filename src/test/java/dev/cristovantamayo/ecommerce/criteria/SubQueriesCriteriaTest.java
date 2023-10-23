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
    public void searchWithAllExercise() {
        /** All products have always been sold at the same price. */

        String jpql ="select distinct p from PurchaseItem i join i.product p where " +
                " i.productPrice = ALL " +
                " (select i2.productPrice from PurchaseItem i2 where i2.product = p and i2.id <> i.id)";

        TypedQuery<Product> typedQueryJpql = entityManager.createQuery(jpql, Product.class);
        List<Product> productsJpql = typedQueryJpql.getResultList();
        Assertions.assertFalse(productsJpql.isEmpty());
        productsJpql.forEach(p -> System.out.println(format("ID: %s, product: %s", p.getId(), p.getName())));

        System.out.println("\n------------------------------------\n");

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<PurchaseItem> root = criteriaQuery.from(PurchaseItem.class);

        criteriaQuery.select(root.get(PurchaseItem_.product));
        criteriaQuery.distinct(true);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<PurchaseItem> subQuryRoot = subquery.from(PurchaseItem.class);
        subquery.select(subQuryRoot.get(PurchaseItem_.productPrice));
        subquery.where(
                criteriaBuilder.equal(subQuryRoot.get(PurchaseItem_.product), root.get(PurchaseItem_.product)),
                criteriaBuilder.notEqual(subQuryRoot, root)
        );

        criteriaQuery.where(
                criteriaBuilder.equal(
                        root.get(PurchaseItem_.productPrice), criteriaBuilder.all(subquery)));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
        list.forEach(obj -> System.out.println(format("ID: %s, product: %s", obj.getId(), obj.getName())));

    }

    @Test
    public void searchWithAny02() {
        /** All products that have already been sold for a different price than the current one.
        *
        *    String jpql = "select p from Product p " +
        *            " where p.price <> ANY (select productPrice from PurchaseItem where product = p)";
        */

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<PurchaseItem> subqueryRoot = subquery.from(PurchaseItem.class);
        subquery.select(subqueryRoot.get(PurchaseItem_.productPrice));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(PurchaseItem_.product), root));

        criteriaQuery.where(
                criteriaBuilder.notEqual(
                        root.get(Product_.price), criteriaBuilder.any(subquery))
        );

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println(format("ID: %s, product: %s", obj.getId(), obj.getName())));

    }

    @Test
    public void searchWithAny01() {
        /** All products that have already been sold at least once at the current price.
        *
        * String jpql = "select p from Product p " +
        *        " where p.price = ANY (select productPrice from PurchaseItem where product = p)";
        */

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<PurchaseItem> subqueryRoot = subquery.from(PurchaseItem.class);
        subquery.select(subqueryRoot.get(PurchaseItem_.productPrice));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(PurchaseItem_.product), root));

        criteriaQuery.where(
                criteriaBuilder.equal(
                        root.get(Product_.price), criteriaBuilder.any(subquery))
        );

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println(format("ID: %s, product: %s", obj.getId(), obj.getName())));

    }

    @Test
    public void searchWithAll02() {
        /** All products were no longer sold after they became more expensive.
        *
        *   String jpql = "select p from Product p where " +
        *        " p.price > ALL (select productPrice from PurchaseItem where product = p)";
        *        " and exists (select 1 from PurchaseItem where product = p)";
        */

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<PurchaseItem> subqueryRoot = subquery.from(PurchaseItem.class);
        subquery.select(subqueryRoot.get(PurchaseItem_.productPrice));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(PurchaseItem_.product), root));

        criteriaQuery.where(
                criteriaBuilder.greaterThan(
                        root.get(Product_.price), criteriaBuilder.all(subquery)),
                criteriaBuilder.exists(subquery)
        );

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println(format("ID: %s, product: %s", obj.getId(), obj.getName())));
    }

    @Test
    public void searchWithAll01() {
        /** All products that have ALWAYS been sold at the current price.
        *
        *    String jpql = "select p from Product p where " +
        *        " p.price = ALL (select productPrice from PurchaseItem where product = p)";
        */

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<PurchaseItem> subqueryRoot = subquery.from(PurchaseItem.class);
        subquery.select(subqueryRoot.get(PurchaseItem_.productPrice));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(PurchaseItem_.product), root));

        criteriaQuery.where(criteriaBuilder.equal(
                root.get(Product_.price), criteriaBuilder.all(subquery)));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println(format("ID: %s, product: %s", obj.getId(), obj.getName())));
    }

    @Test
    public void searchWithSubQueryAndExistsExercis() {
        /** Products that have already been sold at a price different from the initial price.
         */

        final String jpql = "select p from Product p WHERE exists (" +
                      "  select 1 from PurchaseItem i " +
                      "     where i.product = p " +
                      "          and i.productPrice <> p.price)";

        TypedQuery<Product> typedQueryJpql = entityManager.createQuery(jpql, Product.class);
        List<Product> productsJpql = typedQueryJpql.getResultList();
        Assertions.assertFalse(productsJpql.isEmpty());
        productsJpql.forEach((p -> System.out.println(format("Productid: %s, product: %s", p.getId(), p.getName()))));

        System.out.println("\n------------------------------------\n");

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<PurchaseItem> subQueryRoot = subquery.from(PurchaseItem.class);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(
                criteriaBuilder.equal(subQueryRoot.get(PurchaseItem_.product), root),
                criteriaBuilder.notEqual(
                        subQueryRoot.get(PurchaseItem_.productPrice),
                        root.get(Product_.price)
                )
        );

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> products = typedQuery.getResultList();
        Assertions.assertFalse(products.isEmpty());
        products.forEach((p -> System.out.println(format("Productid: %s, product: %s", p.getId(), p.getName()))));
    }

    @Test
    public void searchWithINExercise() {
        /** Purchases that contain category 2 products
         *
         *      String jpql = "select p from Purchase p where p.id IN (" +
         *                 "         select i.purchase.id from PurchaseItem i " +
         *                 "               join i.product pro " +
         *                 "               join pro.categories c " +
         *                 "                   where c.id = 2" +
         *                 "      )";
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
        *        "      join i2.purchase p2 join i2.product pro2 where pro2.price > 100)";
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
