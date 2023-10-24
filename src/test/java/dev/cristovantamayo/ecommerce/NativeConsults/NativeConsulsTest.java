package dev.cristovantamayo.ecommerce.NativeConsults;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Product;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.String.format;

public class NativeConsulsTest extends EntityManagerTest {

    @Test
    public void executeNativeSqlReturningEntityPassingParameters (){

        final String sql = "select prd_id id , prd_name name, prd_description description, " +
                "prd_created_at created_at, prd_updated_at updated_at, prd_price price, prd_photo photo " +
                "from product_ecm where prd_id = :id";

        Query query = entityManager.createNativeQuery(sql, Product.class);
        query.setParameter("id", 201);

        List<Product> list = query.getResultList();

        Assertions.assertFalse(list.isEmpty());
        list.stream().forEach(p -> {
            System.out.println(format("Id: %s, Product: %s", p.getId(), p.getName()));
        });
    }

    @Test
    public void executeNativeSqlReturningEntity (){
        final String sql3 = "select id, name, description, created_at, updated_at, price, photo " +
                "from product_store";

        final String sql2 = "select prd_id id , prd_name name, prd_description description, prd_created_at created_at, prd_updated_at updated_at, prd_price price, prd_photo photo " +
                "from product_ecm";

        final String sql = "select id, name, description, null created_at, null updated_at, price, null photo " +
                "from product_erp";

        Query query = entityManager.createNativeQuery(sql, Product.class);

        List<Product> list = query.getResultList();

        Assertions.assertFalse(list.isEmpty());
        list.stream().forEach(p -> {
            System.out.println(format("Id: %s, Product: %s", p.getId(), p.getName()));
        });
    }

    @Test
    public void executeNativeSql (){
        final String sql = "select p.id, p.name from product p";
        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> list = query.getResultList();

        Assertions.assertFalse(list.isEmpty());
        list.stream().forEach(p -> System.out.println(format("Id: %s, Product: %s", p[0], p[1])));
    }
}
