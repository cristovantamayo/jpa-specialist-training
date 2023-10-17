package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.dto.ProductDTO;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.Product;
import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.EntityManagerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

import static java.lang.String.format;

public class BasicCriteriaTest extends EntityManagerTest {

    @Test
    public void projectResultDTO() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductDTO> criteriaQuery = criteriaBuilder.createQuery(ProductDTO.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(criteriaBuilder
                .construct(ProductDTO.class, root.get("id"), root.get("name")));

        TypedQuery<ProductDTO> typedQuery = entityManager.createQuery(criteriaQuery);
        List<ProductDTO> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
        list.forEach(dto -> System.out.println(format("ID: %s, PRODUCT: %s", dto.getId(), dto.getName())));
    }

    @Test
    public void projectResultTuple() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.multiselect(root.get("id"), root.get("name"));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Tuple> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
        list.forEach(t -> System.out.println(format("ID: %s, PRODUCT: %s", t.get(0), t.get(1))));

        System.out.println("\n-------------------------------------------\n");

        CriteriaQuery<Tuple> criteriaQuery2 = criteriaBuilder.createTupleQuery();
        Root<Product> root2 = criteriaQuery2.from(Product.class);

        criteriaQuery2.select(criteriaBuilder.tuple(root.get("id").alias("id"), root.get("name").alias("name")));

        TypedQuery<Tuple> typedQuery2 = entityManager.createQuery(criteriaQuery2);
        List<Tuple> list2 = typedQuery2.getResultList();
        Assertions.assertFalse(list2.isEmpty());
        list2.forEach(t -> System.out.println(format("ID: %s, PRODUCT: %s", t.get("id"), t.get("name"))));


    }

    @Test
    public void projectResult() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.multiselect(root.get("id"), root.get("name"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
        list.forEach(arr -> System.out.println(format("ID: %s, PRODUCT: %s", arr[0], arr[1])));
    }

    @Test
    public void selectAllProductsExercise() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> products = typedQuery.getResultList();
        Assertions.assertFalse(products.isEmpty());
        products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
    }

    @Test
    public void selectAttributeAsResponse() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        criteriaQuery.select(root.get("client"));
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);
        Client client = typedQuery.getSingleResult();
        Assertions.assertNotNull(client);
        Assertions.assertEquals("Fernando Medeiros", client.getName());



        CriteriaQuery<BigDecimal> criteriaQuery2 = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Purchase> root2 = criteriaQuery2.from(Purchase.class);
        criteriaQuery2.select(root2.get("total"));
        criteriaQuery2.where(criteriaBuilder.equal(root2.get("id"), 1));

        TypedQuery<BigDecimal> typedQuery2 = entityManager.createQuery(criteriaQuery2);
        BigDecimal total = typedQuery2.getSingleResult();
        Assertions.assertEquals(new BigDecimal("2398.00"), total);
    }

    @Test
    public void searchById() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        /** Default root */
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        /** final String jpql = "select p from Purchase p where p.id = 1"; */
        TypedQuery<Purchase> typedQuery = entityManager
                /**.createQuery(jpql, Purchase.class)*/
                .createQuery(criteriaQuery);

        Purchase purchase = typedQuery.getSingleResult();
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
    }
}
