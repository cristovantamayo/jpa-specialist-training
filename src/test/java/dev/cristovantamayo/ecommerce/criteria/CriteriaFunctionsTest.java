package dev.cristovantamayo.ecommerce.criteria;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.Client_;
import dev.cristovantamayo.ecommerce.model.Product;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.String.format;

public class CriteriaFunctionsTest extends EntityManagerTest {

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
