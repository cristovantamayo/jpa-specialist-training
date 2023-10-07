package dev.cristovantamayo.ecommerce.relationShips;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Test;

public class OptionalAttributeTest extends EntityManagerTest {

    @Test
    public void relationshipValidation() {
        Purchase purchase = entityManager.find(Purchase.class , 1);
    }
}
