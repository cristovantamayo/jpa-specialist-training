package dev.cristovantamayo.ecommerce.relationShips;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Category;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class EagerAndLazyLoaderTest extends EntityManagerTest {

    @Test
    public void relationshipValidation() {
        Purchase purchase = entityManager.find(Purchase.class , 1);

        purchase.getPurchaseItems().isEmpty();

    }
}
