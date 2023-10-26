package dev.cristovantamayo.ecommerce.NativeConsults;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.dto.ProductDTO;
import dev.cristovantamayo.ecommerce.model.Category;
import dev.cristovantamayo.ecommerce.model.Product;
import dev.cristovantamayo.ecommerce.model.PurchaseItem;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.String.format;

public class NativeConsulsTest extends EntityManagerTest {

    @Test
    public void useNamedNativeQuery02 (){
        Query query = entityManager.createNamedQuery("ecm_category.list");

        List<Category> list = query.getResultList();

        Assertions.assertFalse(list.isEmpty());
        list.stream().forEach(c -> {
            System.out.println(format("Category => Id: %s, Name: %s", c.getId(), c.getName()));
        });
    }

    @Test
    public void useNamedNativeQuery01 (){
        Query query = entityManager.createNamedQuery("product_store.list");

        List<Product> list = query.getResultList();

        Assertions.assertFalse(list.isEmpty());
        list.stream().forEach(p -> {
            System.out.println(format("Id: %s, Product: %s", p.getId(), p.getName()));
        });
    }

    @Test
    public void useColumnResultReturningDTO() {
        final String sql = "select * from product_ecm ";

        Query query = entityManager.createNativeQuery(sql, "product_ecm.ProductDTO");

        List<ProductDTO> list = query.getResultList();

        Assertions.assertFalse(list.isEmpty());
        list.stream().forEach(p -> {
            System.out.println(format("ProductDTO > Id: %s, Name: %s", p.getId(), p.getName()));
        });
    }

    @Test
    public void executeNativeSqlWithFieldResult () {
        final String sql = "select * " + //id, name, description, created_at, updated_at, price, photo
                "from product_ecm ";

        Query query = entityManager.createNativeQuery(sql, "product_store.Product");

        List<Product> list = query.getResultList();

        Assertions.assertFalse(list.isEmpty());
        list.stream().forEach(p -> {
            System.out.println(format("Id: %s, Product: %s", p.getId(), p.getName()));
        });
    }

    @Test
    public void executeNativeSqlWithSQLResultSetMapping02 (){
        final String sql = "select * from purchase_item ip join product p on p.id = ip.product_id";

        Query query = entityManager.createNativeQuery(sql, "purchase_item-product.PurchaseItem-Product");

        List<Object[]> list = query.getResultList();

        Assertions.assertFalse(list.isEmpty());
        list.stream().forEach(arr -> {
            System.out.println(format("PurchaseId: %s, Id: %s, Product: %s", ((PurchaseItem)arr[0]).getId().getPurchaseId(),
                    ((Product)arr[1]).getId(), ((Product)arr[1]).getName()));
        });
    }

    @Test
    public void executeNativeSqlWithSQLResultSetMapping01 (){
        final String sql = "select id, name, description, created_at, updated_at, price, photo " +
                "from product_store";

        Query query = entityManager.createNativeQuery(sql, "product_store.Product");

        List<Product> list = query.getResultList();

        Assertions.assertFalse(list.isEmpty());
        list.stream().forEach(p -> {
            System.out.println(format("Id: %s, Product: %s", p.getId(), p.getName()));
        });
    }

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
