package dev.cristovantamayo.ecommerce.NativeConsults;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.Product;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class StoredProceduresTest extends EntityManagerTest {

    @Test
    public void receiveListFormNamedProcedure() {
        StoredProcedureQuery storedProcedureQuery = entityManager
                .createNamedStoredProcedureQuery("sold_above_average");

        storedProcedureQuery.setParameter("p_year", 2023);

        List<Client> clients = storedProcedureQuery.getResultList();

        Assertions.assertFalse(clients.isEmpty());
    }

    @Test
    public void invokeProcedureExercise() {

        final Integer productId = 1;
        final BigDecimal percentage = new BigDecimal(0.1);

        Product product = entityManager.find(Product.class, productId);

        StoredProcedureQuery storedProcedureQuery = entityManager
                .createStoredProcedureQuery("adjust_product_price", Product.class);

        storedProcedureQuery.registerStoredProcedureParameter(
                "product_id", Integer.class, ParameterMode.IN);

        storedProcedureQuery.registerStoredProcedureParameter(
                "percentage_adjustment", BigDecimal.class, ParameterMode.IN);

        storedProcedureQuery.registerStoredProcedureParameter(
                "adjusted_price", BigDecimal.class, ParameterMode.OUT);

        storedProcedureQuery.setParameter("product_id", productId);

        storedProcedureQuery.setParameter("percentage_adjustment", percentage);

        BigDecimal adjustedPrice = (BigDecimal) storedProcedureQuery.getOutputParameterValue("adjusted_price");

        Assertions.assertEquals(calculatePriceAdjust(percentage, product), adjustedPrice.setScale(3, RoundingMode.DOWN));
    }

    private static BigDecimal calculatePriceAdjust(BigDecimal percentage, Product product) {
        return (product.getPrice().multiply(percentage.add(new BigDecimal(1)))).setScale(3, RoundingMode.DOWN);
    }

    @Test
    public void receiveListFormProcedure() {
        StoredProcedureQuery storedProcedureQuery = entityManager
                .createStoredProcedureQuery("sold_above_average", Client.class);

        storedProcedureQuery.registerStoredProcedureParameter(
                "p_year", Integer.class, ParameterMode.IN);

        storedProcedureQuery.setParameter("p_year", 2023);

        List<Client> clients = storedProcedureQuery.getResultList();

        Assertions.assertFalse(clients.isEmpty());
    }

    @Test
    public void useParametersInAndOut() {
        StoredProcedureQuery storedProcedureQuery = entityManager
                .createStoredProcedureQuery("search_name_product");

        storedProcedureQuery.registerStoredProcedureParameter(
                "product_id", Integer.class, ParameterMode.IN);

        storedProcedureQuery.registerStoredProcedureParameter(
                "product_name", String.class, ParameterMode.OUT);

        storedProcedureQuery.setParameter("product_id", 1);

        String productName = (String) storedProcedureQuery.getOutputParameterValue("product_name");

        Assertions.assertEquals("Kindle", productName);
    }
}
