package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static java.lang.String.format;

public class ConditionalsExpressionsTest extends EntityManagerTest {

    @Test
    public void useInExpression02(){

        Client client01 = entityManager.find(Client.class, 1);
        Client client02 = new Client();
        client02.setId(2);

        List<Client> clients = Arrays.asList(client01, client02);
        // List<Integer> clientsIds = Arrays.asList(client01.getId(), client02.getId());

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        criteriaQuery.select(root);

        criteriaQuery.where(root.get(Purchase_.CLIENT).in(clients));
        // criteriaQuery.where(root.get(Purchase_.CLIENT).get(Client_.ID).in(clientsIds));

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
    }

    @Test
    public void useInExpression01(){

        List<Integer> ids = Arrays.asList(1, 3, 4, 6);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        criteriaQuery.select(root);

        criteriaQuery.where(root.get(Purchase_.ID).in(ids));

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
    }

    @Test
    public void useCaseExpression() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        criteriaQuery.multiselect(
                root.get(Purchase_.ID),
//                criteriaBuilder.selectCase(root.get(Purchase_.STATUS))
//                        .when(StatusPurchase.PAID_OUT, "Was paid.")
//                        .when(StatusPurchase.WAITING, "It's waiting.")
//                        .otherwise(root.get(Purchase_.STATUS)).as(String.class)
                criteriaBuilder.selectCase(root.get(Purchase_.PAYMENT).type().as(String.class))
                        .when("BankSlip", "It was paid by bank slip.")
                        .when("CredCard", "It was paid by card")
                        .otherwise("Not identified")
        );

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> lista = typedQuery.getResultList();
        Assertions.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void orderingResults() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Client_.NAME)));

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Client> clients = typedQuery.getResultList();
        Assertions.assertFalse(clients.isEmpty());
        clients.forEach(c -> System.out.println(c.getName()));
    }

    @Test
    public void useOperatorOR() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.or(
                    criteriaBuilder.equal(root.get(Purchase_.STATUS), PurchaseStatus.WAITING),
                    criteriaBuilder.equal(root.get(Purchase_.STATUS), PurchaseStatus.PAID_OUT)
                ),
                criteriaBuilder.greaterThan(root.get(Purchase_.TOTAL), new BigDecimal(499))
        );

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
        purchases.forEach(p ->
                p.getPurchaseItems().forEach(i ->
                        System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s",
                                i.getProduct().getId(), i.getProduct().getName(), i.getProduct().getPrice()))));
    }

    @Test
    public void useOperatorAND() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.and(
                    criteriaBuilder.greaterThan(root.get(Purchase_.TOTAL), new BigDecimal(499)),
                    criteriaBuilder.equal(root.get(Purchase_.STATUS), PurchaseStatus.PAID_OUT)
                ),
                criteriaBuilder.greaterThan(root.get(Purchase_.purchaseDate), LocalDateTime.now().minusDays(5))
        );

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
        purchases.forEach(p ->
                p.getPurchaseItems().forEach(i ->
                        System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s",
                                i.getProduct().getId(), i.getProduct().getName(), i.getProduct().getPrice()))));
    }

    @Test
    public void useDifference() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Purchase_.TOTAL), new BigDecimal(880)));

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
        purchases.forEach(p ->
                p.getPurchaseItems().forEach(i ->
                        System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s",
                                i.getProduct().getId(), i.getProduct().getName(), i.getProduct().getPrice()))));
    }

    @Test
    public void useBetweenDates() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        // Calendar initialDate =  Calendar.getInstance();
        // initialDate.add(Calendar.DATE, -30);
        // Calendar lastDate = Calendar.getInstance();
        // lastDate.add(Calendar.DATE, -2);

        criteriaQuery.select(root);

        //criteriaQuery.where(criteriaBuilder.between(root.get(Purchase_.PURCHASE_DATE), initialDate, lastDate));

        criteriaQuery.where(criteriaBuilder.between(
                root.get(Purchase_.PURCHASE_DATE),
                LocalDateTime.now().minusDays(5).withSecond(0).withMinute(0).withHour(0),
                LocalDateTime.now()
        ));

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
        purchases.forEach(p ->
                p.getPurchaseItems().forEach(i ->
                        System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s",
                                i.getProduct().getId(), i.getProduct().getName(), i.getProduct().getPrice()))));
    }

    @Test
    public void useBetweenQuantities() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.between(root.get(Purchase_.TOTAL), new BigDecimal(799), new BigDecimal(3500)));

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
        purchases.forEach(p ->
                p.getPurchaseItems().forEach(i ->
                        System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s",
                                i.getProduct().getId(), i.getProduct().getName(), i.getProduct().getPrice()))));
    }

    @Test
    public void useGreaterAndLessExpressionsForDates() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        Calendar initialDate =  Calendar.getInstance();
        initialDate.add(Calendar.DATE, -3);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.lessThan(root.get(Purchase_.PURCHASE_DATE), initialDate.getTime()));

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
        purchases.forEach(p ->
                p.getPurchaseItems().forEach(i ->
                        System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s",
                                i.getProduct().getId(), i.getProduct().getName(), i.getProduct().getPrice()))));
    }

    @Test
    public void useMajorAndMinorExpression() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.greaterThan(root.get(Product_.PRICE), new BigDecimal(799)),
                criteriaBuilder.lessThan(root.get(Product_.PRICE), new BigDecimal(3500))
        );

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> products = typedQuery.getResultList();
        Assertions.assertFalse(products.isEmpty());
        products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
    }

    @Test
    public void useMinorExpression() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.lessThan(root.get(Product_.PRICE), new BigDecimal(799)));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> products = typedQuery.getResultList();
        Assertions.assertFalse(products.isEmpty());
        products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
    }

    @Test
    public void useMajorExpression() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get(Product_.PRICE), new BigDecimal(2200)));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> products = typedQuery.getResultList();
        Assertions.assertFalse(products.isEmpty());
        products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
    }

    @Test
    public void useIsNUll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);
        // criteriaQuery.where(root.get(Product_.PHOTO).isNull());
        // criteriaQuery.where(root.get(Product_.PHOTO).isNotNull());
        criteriaQuery.where(criteriaBuilder.isNull(root.get(Product_.PHOTO)));
        // criteriaQuery.where(criteriaBuilder.isNotNull(root.get(Product_.PHOTO)));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> products = typedQuery.getResultList();
        Assertions.assertFalse(products.isEmpty());
        products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
    }

    @Test
    public void useIsEmpty() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.isEmpty(root.get(Product_.CATEGORIES)));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> products = typedQuery.getResultList();
        Assertions.assertFalse(products.isEmpty());
        products.forEach(p -> System.out.println(format("ID: %s, PRODUCT: %s, PRICE: %s", p.getId(), p.getName(), p.getPrice())));
    }

    @Test
    public void useConditionalLikeExpression() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.like(root.get(Client_.name), "%a%"));

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Client> clients = typedQuery.getResultList();
        Assertions.assertFalse(clients.isEmpty());
        clients.forEach(c -> System.out.println(c.getName()));
    }
}
