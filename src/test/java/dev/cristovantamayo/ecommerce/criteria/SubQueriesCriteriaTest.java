package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class SubQueriesCriteriaTest extends EntityManagerTest {

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
