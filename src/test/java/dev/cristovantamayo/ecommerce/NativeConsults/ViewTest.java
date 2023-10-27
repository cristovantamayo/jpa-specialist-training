package dev.cristovantamayo.ecommerce.NativeConsults;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ViewTest extends EntityManagerTest {

    @Test
    public void executeView() {
        Query query = entityManager.createNativeQuery(
                "select cli.id, cli.name, sum(pur.total) " +
                        " from purchase pur " +
                        " join view_clients_above_average cli on cli.id = pur.client_id " +
                        " group by pur.client_id");

        List<Object[]> list = query.getResultList();

        list.stream().forEach(arr -> System.out.println(
                String.format("Cliente => ID: %s, Nome: %s, Total: %s", arr)));
    }

    @Test
    public void executeViewReturningClient() {
        Query query = entityManager.createNativeQuery(
                "select * from view_clients_above_average", Client.class);

        List<Client> list = query.getResultList();

        list.stream().forEach(obj -> System.out.println(
                String.format("Client => ID: %s, Nome: %s", obj.getId(), obj.getName())));
    }
}
