package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static java.lang.String.format;

public class GroupingResultsTest extends EntityManagerTest {

    @Test
    public void conditionGroupingWithHaving() {
        /** Total sold across top-selling categories.
        * String jpql = "select cat.name, sum(ip.productPrice) from PurchaseIten ip " +
        *        " join ip.product pro " + 
        *        " join pro.categories cat " +
        *        " group by cat.id " +
        *        " having sum(ip.productPrice) > 100 "; */

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<PurchaseItem> root = criteriaQuery.from(PurchaseItem.class);
        Join<PurchaseItem, Product> joinProduct = root.join(PurchaseItem_.PRODUCT);
        Join<Product, Category> joinProductCategory = joinProduct.join(Product_.CATEGORIES);

        criteriaQuery.multiselect(
                joinProductCategory.get(Category_.NAME),
                criteriaBuilder.sum(root.get(PurchaseItem_.PRODUCT_PRICE)),
                criteriaBuilder.avg(root.get(PurchaseItem_.PRODUCT_PRICE))
        );

        criteriaQuery.groupBy(joinProductCategory.get(Category_.ID));

        criteriaQuery.having(
                criteriaBuilder.greaterThan(
                        criteriaBuilder.avg(root.get(PurchaseItem_.PRODUCT_PRICE)).as(BigDecimal.class),
                        new BigDecimal(800)
                )
        );
        
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();

        list.forEach(arr -> System.out.println(
                "Category Name: " + arr[0]
                        + ", SUM: " + arr[1]
                        + ", AVG: " + arr[2]));
    }

    @Test
    public void groupingResultsWithFunctions() {
//         Total sold by Month.
//        String jpql = "select concat(year(p.purchaseDate), '/', function('monthname', p.purchaseDate)), sum(p.total) " +
//                " from Purchase p " +
//                " group by year(p.purchaseDate), month(p.purchaseDate) ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        Expression<Integer> purchaseCreationYear = criteriaBuilder
                .function("year", Integer.class, root.get(Purchase_.PURCHASE_DATE));
        Expression<Integer> purchaseCreationMonth = criteriaBuilder
                .function("month", Integer.class, root.get(Purchase_.PURCHASE_DATE));
        Expression<String> purchaseCreationMonthName = criteriaBuilder
                .function("monthname", String.class, root.get(Purchase_.PURCHASE_DATE));

        Expression<String> yearMonthConcat = criteriaBuilder.concat(
                criteriaBuilder.concat(purchaseCreationYear.as(String.class), "/"),
                purchaseCreationMonthName
        );

        criteriaQuery.multiselect(
                yearMonthConcat,
                criteriaBuilder.sum(root.get(Purchase_.TOTAL))
        );

        criteriaQuery.groupBy(yearMonthConcat, purchaseCreationYear, purchaseCreationMonth);

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();

        list.forEach(arr -> System.out.println("Year/Month: " + arr[0] + ", Sum: " + arr[1]));
    }

    @Test
    public void groupingResultsExercise () {
        /** Total sold vy Client
         *
         * String jpql = "select c.name, sum(i.productPrice) from PurchaseItem i " +
         *      "join i.purchase p " +
         *      "join p.client c " +
         *      "group by c.id";
         */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<PurchaseItem> root = criteriaQuery.from(PurchaseItem.class);
        Join<PurchaseItem, Purchase> joinPurchase = root.join(PurchaseItem_.PURCHASE);
        Join<Purchase, Client> joinClient = joinPurchase.join(Purchase_.CLIENT);

        criteriaQuery.multiselect(
                joinClient.get(Client_.ID),
                joinClient.get(Client_.NAME),
                criteriaBuilder.sum(root.get(PurchaseItem_.productPrice))
        );

        criteriaQuery.groupBy(joinClient.get(Client_.ID));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();

        list.forEach(arr -> System.out.println(format("Id: %s, Name: %s, Count: %s", arr[0], arr[1], arr[2])));
    }

    @Test
    public void groupingResults02 () {
        /** Total sold by Category
         *
         * String jpql = "select c.name, sum(i.productPrice) from PurchaseItem i " +
         *      "join i.product pro join pro.categories c " +
         *      "group by c.id";
         */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<PurchaseItem> root = criteriaQuery.from(PurchaseItem.class);
        Join<PurchaseItem, Product> joinItem = root.join(PurchaseItem_.product);
        Join<Product, Category> joinProductCategory = joinItem.join(Product_.CATEGORIES);

        criteriaQuery.multiselect(
                joinProductCategory.get(Category_.ID),
                joinProductCategory.get(Category_.NAME),
                criteriaBuilder.sum(root.get(PurchaseItem_.productPrice))
        );

        criteriaQuery.groupBy(joinProductCategory.get(Category_.ID));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();

        list.forEach(arr -> System.out.println(format("Id: %s, Name: %s, Count: %s", arr[0], arr[1], arr[2])));
    }

    @Test
    public void groupingResults01 () {
        /** Number of products by category
         *
         * String jpql = "select c.name, count(p.id) from Category c " +
         *          "join c.products p " +
         *          "group by c.id";
         */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Category> root = criteriaQuery.from(Category.class);
        Join<Category, Product> joinProduct = root.join(Category_.PRODUCTS, JoinType.LEFT);

        criteriaQuery.multiselect(
                root.get(Category_.NAME),
                criteriaBuilder.count(joinProduct.get(Product_.ID))
        );

        criteriaQuery.groupBy(root.get(Category_.ID));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();

        list.forEach(arr -> System.out.println(format("Name: %s, Count: %s", arr[0], arr[1])));
    }
}
