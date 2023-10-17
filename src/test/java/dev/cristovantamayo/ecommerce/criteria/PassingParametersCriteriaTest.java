package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Invoice;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PassingParametersCriteriaTest extends EntityManagerTest {

    @Test
    public void passingParameter() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = criteriaQuery.from(Purchase.class);

        criteriaQuery.select(root);
        ParameterExpression<Integer> parameterExpressionId = criteriaBuilder.parameter(Integer.class);
        // ParameterExpression<Integer> parameterExpressionId2 = criteriaBuilder.parameter(Integer.class, "myId");
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), parameterExpressionId));


        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter(parameterExpressionId, 1);
        // typedQuery.setParameter("myId", 1);

        List<Purchase> purchases = typedQuery.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
    }

    @Test
    public void passingParameterDate() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Invoice> criteriaQuery = criteriaBuilder.createQuery(Invoice.class);
        Root<Invoice> root = criteriaQuery.from(Invoice.class);

        criteriaQuery.select(root);
        ParameterExpression<Date> parameterExpressionDate = criteriaBuilder.parameter(Date.class, "initialDate");
        criteriaQuery.where(criteriaBuilder.greaterThan(root.get("issueDate"), parameterExpressionDate));

        Calendar initialDate = Calendar.getInstance();
        initialDate.add(Calendar.DATE, -30);

        TypedQuery<Invoice> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter("initialDate", initialDate.getTime(), TemporalType.TIMESTAMP);

        List<Invoice> invoices = typedQuery.getResultList();
        Assertions.assertFalse(invoices.isEmpty());
    }
}
