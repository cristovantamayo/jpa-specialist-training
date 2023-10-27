package dev.cristovantamayo.ecommerce.NativeConsults;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StoredProceduresTest extends EntityManagerTest {

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
