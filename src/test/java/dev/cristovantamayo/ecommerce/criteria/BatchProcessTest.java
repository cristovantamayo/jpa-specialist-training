package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Category;
import dev.cristovantamayo.ecommerce.model.Category_;
import dev.cristovantamayo.ecommerce.model.Product;
import dev.cristovantamayo.ecommerce.model.Product_;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BatchProcessTest extends EntityManagerTest {

    @Test
    public void batchUpdate() {
        entityManager.getTransaction().begin();

        /** String jpql = "update Product p set p.price = p.price + (p.price * 0.1) " +
        *        "where exists (select 1 from p.categories c2 where c2.id = :category)";
        */

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Product> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Product.class);
        Root<Product> root = criteriaUpdate.from(Product.class);

        criteriaUpdate.set(root.get(Product_.price),
                criteriaBuilder.prod(root.get(Product_.price), new BigDecimal("1.1")));

        Subquery<Integer> subquery = criteriaUpdate.subquery(Integer.class);
        Root<Product> subQueryRoot = subquery.correlate(root);
        Join<Product, Category> categoryJoin = subQueryRoot.join(Product_.categories);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(criteriaBuilder.equal(categoryJoin.get(Category_.id), 2));

        criteriaUpdate.where(criteriaBuilder.exists(subquery));

        Query query = entityManager.createQuery(criteriaUpdate);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }
}
