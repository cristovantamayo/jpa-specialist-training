package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.jupiter.api.Test;

import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class BatchOperationsTest extends EntityManagerTest {

    private static final int LIMIT_INSERTIONS = 4;

    @Test
    public void batchUpdate() {
        entityManager.getTransaction().begin();

        String jpql2 = "update Product p set p.price = p.price + 1 where p.id between 1 and 10";

        String jpql = "update Product p set p.price = p.price + (p.price * 0.1) " +
                "where exists (" +
                "   select 1 from p.categories c2 where c2.id = :category" +
                ")";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("category", 1);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }

    @Test
    public void batchInsert() {
        InputStream in = BatchOperationsTest.class.getClassLoader()
                .getResourceAsStream("products/import.txt");

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        entityManager.getTransaction().begin();

        int insertionsCount = 0;

        for(String linha: reader.lines().collect(Collectors.toList())) {
            if (linha.isBlank()) {
                continue;
            }

            String[] productColuna = linha.split(";");
            Product product = new Product();
            product.setName(productColuna[0]);
            product.setDescription(productColuna[1]);
            product.setPrice(new BigDecimal(productColuna[2]));
            product.setCreatedAt(LocalDateTime.now());

            entityManager.persist(product);

            if (++insertionsCount == LIMIT_INSERTIONS) {
                entityManager.flush();
                entityManager.clear();

                insertionsCount = 0;

                System.out.println("---------------------------------");
            }
        }

        entityManager.getTransaction().commit();
    }
}
