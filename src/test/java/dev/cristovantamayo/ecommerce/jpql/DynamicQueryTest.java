package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.persistence.TypedQuery;
import java.util.List;

import static java.lang.String.format;

public class DynamicQueryTest extends EntityManagerTest {

    @Test
    public void dynamicQueryExecution() {
        Product consumed = new Product();
        consumed.setName("gopro");

        List<Product> products = search(consumed);

        Assertions.assertFalse(products.isEmpty());
        Assertions.assertEquals("Câmera GoPro Hero 7", products.get(0).getName());
        products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
    }

    private List<Product> search(Product consumed) {
        StringBuilder jpql = new StringBuilder("Select p from Product p where 1 = 1 ");
        if(consumed.getName() != null)
            jpql.append("and p.name like concat('%', :name, '%') ");

        if(consumed.getDescription() != null)
            jpql.append("and p.description like concat('%', :description,'%')");

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql.toString(), Product.class);

        if(consumed.getName() != null)
            typedQuery.setParameter("name", consumed.getName());

        if(consumed.getDescription() != null)
            typedQuery.setParameter("description", consumed.getDescription());

        return typedQuery.getResultList();

    }
}
