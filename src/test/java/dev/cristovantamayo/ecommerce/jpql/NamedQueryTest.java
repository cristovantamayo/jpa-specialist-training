package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static java.lang.String.format;

public class NamedQueryTest extends EntityManagerTest {

    @Test
    public void executeSearch() {

        final Integer categoryId = 1;

        TypedQuery<Product> typedQuery = entityManager.createNamedQuery("Product.list", Product.class);
        List<Product> products = typedQuery.getResultList();

        TypedQuery<Product> typedQueryByCategory = entityManager.createNamedQuery("Product.listByCategory", Product.class);
        typedQueryByCategory.setParameter("categoryId", categoryId);
        List<Product> productsByCategory = typedQueryByCategory.getResultList();

        Assertions.assertFalse(products.isEmpty());
        products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
        System.out.println("----------------------------\nByCategory: "+categoryId+"\n");
        Assertions.assertFalse(productsByCategory.isEmpty());
        productsByCategory.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
    }
}
