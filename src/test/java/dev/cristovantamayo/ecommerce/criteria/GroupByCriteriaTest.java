package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.model.Purchase_;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GroupByCriteriaTest extends EntityManagerTest {

    @Test
    public void groupingResultsWithFunctions() {
//         Total sold by Month.
//        String jpql = "select concat(year(p.purchaseDate), '/', function('monthname', p.purchaseDate)), sum(p.total) " +
//                " from Purchase p " +
//                " group by year(p.purchaseDate), month(p.purchaseDate) ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        Expression<Integer> purchaseCreationYear = criteriaBuilder
                .function("year", Integer.class, root.get(Purchase_.PURCHASE_DATE));
        Expression<Integer> purchaseCreationMonth = criteriaBuilder
                .function("month", Integer.class, root.get(Purchase_.PURCHASE_DATE));
        Expression<String> purchaseCreationMonthName = criteriaBuilder
                .function("monthname", String.class, root.get(Purchase_.PURCHASE_DATE));

        Expression<String> yearMonthConcat = criteriaBuilder.concat(
                criteriaBuilder.concat(purchaseCreationYear.as(String.class), "/"),
                purchaseCreationMonthName
        );

        criteriaQuery.multiselect(
                yearMonthConcat,
                criteriaBuilder.sum(root.get(Purchase_.TOTAL))
        );

        criteriaQuery.groupBy(yearMonthConcat, purchaseCreationYear, purchaseCreationMonth);

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();

        list.forEach(arr -> System.out.println("Year/Month: " + arr[0] + ", Sum: " + arr[1]));
    }
}
