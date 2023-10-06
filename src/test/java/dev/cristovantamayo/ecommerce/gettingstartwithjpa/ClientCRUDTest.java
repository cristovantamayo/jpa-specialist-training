package dev.cristovantamayo.ecommerce.gettingstartwithjpa;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.ClientGender;
import org.junit.Assert;
import org.junit.Test;

public class ClientCRUDTest extends EntityManagerTest {

    private Client client =
            Client.of(null, "Joshua Tenens", ClientGender.MAN, null);
    @Test
    public void clientInsertion() {

        create(client);

        Client expectedClient = client;
        Client actualClient = entityManager.find(Client.class, client.getId());

        Assert.assertEquals(expectedClient.getName(), actualClient.getName());
        Assert.assertEquals(expectedClient.getId(), actualClient.getId());
    }

    @Test
    public void clientReading() {

        Client actualClient = null;

        actualClient = read(1);

        Assert.assertNotNull(actualClient);
    }

    @Test
    public void clientUpdate() {

        Integer theSameIndex = 1;

        Client firstClient = read(theSameIndex);
        firstClient.setName(client.getName());

        firstClient = update(firstClient);

        Client actualClient = read(theSameIndex);

        Assert.assertEquals(client.getName(), actualClient.getName());
        Assert.assertEquals(firstClient.getName(), actualClient.getName());
    }

    @Test
    public void clientDeletion() {

        Client firstClient = read(1);

        delete(firstClient);

        Client actualClient = read(firstClient.getId());

        Assert.assertNull(actualClient);
    }

    private void create(Client innerclient) {
        entityManager.getTransaction().begin();
        entityManager.persist(innerclient);
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    private Client read(Integer id) {
        return entityManager.find(Client.class, id);
    }


    private Client update(Client firstClient) {
        entityManager.getTransaction().begin();
        firstClient = entityManager.merge(firstClient);
        entityManager.getTransaction().commit();
        entityManager.clear();
        return firstClient;
    }

    private void delete(Client firstClient) {
        entityManager.getTransaction().begin();
        firstClient.getPurchases().forEach(i -> {
            i.getPurchaseItems().forEach(j -> entityManager.remove(j));
            entityManager.remove(i);
        });
        entityManager.remove(firstClient);
        entityManager.getTransaction().commit();
    }
}