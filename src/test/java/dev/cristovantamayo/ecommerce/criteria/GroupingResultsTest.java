package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.String.format;

public class GroupingResultsTest extends EntityManagerTest {

    @Test
    public void groupingResults02 () {
        /** Total sold by Category
         *
         * String jpql = "select c.name, sum(i.productPrice) from PurchaseItem i "
         *      "join i.product pro join pro.categories c "
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
         * String jpql = "select c.name, count(p.id) from Category c "
         *          "join c.products p "
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
