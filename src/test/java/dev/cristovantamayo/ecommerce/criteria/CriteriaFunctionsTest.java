package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.List;

import static java.lang.String.format;

public class CriteriaFunctionsTest extends EntityManagerTest {

    @Test
    public void applyNumberFunctions() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        criteriaQuery.multiselect(
                root.get(Purchase_.ID),
                criteriaBuilder.abs(criteriaBuilder.prod(root.get(Purchase_.ID), -1)),
                criteriaBuilder.mod(root.get(Purchase_.ID), 2),
                criteriaBuilder.sqrt(root.get(Purchase_.TOTAL))
        );

        criteriaQuery.where(criteriaBuilder
                .greaterThan(criteriaBuilder.sqrt(root.get(Purchase_.TOTAL)), 3.0));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
        list.forEach(arr -> {
            System.out.println(format("default: %s\nabs: %s\nmod: %s\nsqrt: %s", arr[0], arr[1], arr[2], arr[3]));
            System.out.println("----------------------------------------");
        });
    }

    @Test
    public void applyDateFunctions() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);
        Join<Purchase, Payment> joinPayment = root.join(Purchase_.payment);
        Join<Purchase, PaymentTicket> joinPaymentTicket =
                criteriaBuilder.treat(joinPayment, PaymentTicket.class);

        criteriaQuery.multiselect(
                root.get(Purchase_.ID),
                criteriaBuilder.currentDate(),
                criteriaBuilder.currentTime(),
                criteriaBuilder.currentTimestamp()
        );

        criteriaQuery.where(
            criteriaBuilder.between(
                    criteriaBuilder.currentDate(),
                            root.get(Purchase_.PURCHASE_DATE).as(java.sql.Date.class),
                            joinPaymentTicket.get(PaymentTicket_.DUE_DATE).as(java.sql.Date.class)),
                    criteriaBuilder.equal(root.get(Purchase_.STATUS), PurchaseStatus.WAITING)
        );

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
        list.forEach(arr -> {
            System.out.println("----------------------------");
            System.out.println(format("default: %s\ncurrent_date: %s\ncurrent_time: %s\ncurrent_timestamp: %s",
                    arr[0], arr[1], arr[2], arr[3]));
        });
    }

    @Test
    public void applyStringFunction() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.multiselect(
                root.get(Client_.NAME),
                criteriaBuilder.concat("Client Name: ", root.get(Client_.NAME)),
                criteriaBuilder.length(root.get(Client_.NAME)),
                criteriaBuilder.locate(root.get(Client_.NAME), "a"),
                criteriaBuilder.substring(root.get(Client_.NAME), 1, 3),
                criteriaBuilder.lower(root.get(Client_.NAME)),
                criteriaBuilder.upper(root.get(Client_.NAME)),
                criteriaBuilder.trim(root.get(Client_.NAME))
        );

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assertions.assertFalse(list.isEmpty());
        list.forEach(arr -> {
            System.out.println("----------------------------");
            System.out.println(format("default: %s\nconcat: %s\nlength: %s\nlocate: %s\nsubstring: %s\nlower: %s\nupper: %s\ntrim: |%s|",
                arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7]));
            });
    }
}
