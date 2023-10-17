package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Product;
import dev.cristovantamayo.ecommerce.model.Product_;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.String.format;

public class PaginationCriteriaTest extends EntityManagerTest {

    @Test
    public void paginateResults() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        for(int i=1; i<=5; i++) {
            Integer page = i;
            Integer perPage = 2;
            Integer toPage = perPage + page - 1;

            typedQuery.setFirstResult(toPage);
            typedQuery.setMaxResults(perPage);

            List<Product> products = typedQuery.getResultList();
            Assertions.assertFalse(products.isEmpty());
            products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
            System.out.println("---------------");
            System.out.println("");
        }
    }
}
