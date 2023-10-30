package dev.cristovantamayo.ecommerce.importantdetails;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.Client_;
import dev.cristovantamayo.ecommerce.model.Purchase;
import dev.cristovantamayo.ecommerce.model.Purchase_;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import org.hibernate.graph.SubGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityGraphTest extends EntityManagerTest {

    @Test
    public void LookingForEssentialsAttributesOfPurchase04 () {

        EntityGraph<Purchase> entityGraph =
                entityManager.createEntityGraph(Purchase.class);

        entityGraph.addAttributeNodes(Purchase_.purchaseDate, Purchase_.status, Purchase_.total, Purchase_.invoice);

        SubGraph<Client> clientSubGraph = (SubGraph<Client>) entityGraph.addSubgraph(Purchase_.client);
        clientSubGraph.addAttributeNodes(Client_.name, Client_.cpf);

        TypedQuery<Purchase> typedQuery =  entityManager
                .createQuery("select p from Purchase p where p.id = 1", Purchase.class);

        List<Purchase> purchases = typedQuery
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();

        Assertions.assertFalse(purchases.isEmpty());

    }

    @Test
    public void LookingForEssentialsAttributesOfPurchase03 () {

        EntityGraph<Purchase> entityGraph =
                entityManager.createEntityGraph(Purchase.class);

        entityGraph.addAttributeNodes("purchaseDate", "status", "total", "invoice");

        SubGraph<Client> clientSubGraph = (SubGraph<Client>) entityGraph.addSubgraph("client", Client.class);
        clientSubGraph.addAttributeNodes("name", "cpf");

        List<Purchase> purchases = entityManager.createQuery("select p from Purchase p where p.id = 1")
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();

        Assertions.assertFalse(purchases.isEmpty());

    }

    @Test
    public void LookingForEssentialsAttributesOfPurchase02 () {

        EntityGraph<Purchase> entityGraph =
                entityManager.createEntityGraph(Purchase.class);

        entityGraph.addAttributeNodes("purchaseDate", "status", "total", "invoice");

        List<Purchase> purchases = entityManager.createQuery("select p from Purchase p where p.id = 1")
                        .setHint("javax.persistence.fetchgraph", entityGraph)
                                .getResultList();

        Assertions.assertFalse(purchases.isEmpty());

    }

    @Test
    public void LookingForEssentialsAttributesOfPurchase01 () {

        EntityGraph<Purchase> entityGraph =
                entityManager.createEntityGraph(Purchase.class);

        entityGraph.addAttributeNodes("purchaseDate", "status", "total", "invoice");

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        //properties.put("javax.persistence.loadgraph", entityGraph);

        Purchase purchase = entityManager.find(Purchase.class, 1, properties);
        Assertions.assertNotNull(purchase);

    }
}
