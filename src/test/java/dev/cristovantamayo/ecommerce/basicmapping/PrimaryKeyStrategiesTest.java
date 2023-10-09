package dev.cristovantamayo.ecommerce.basicmapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrimaryKeyStrategiesTest extends EntityManagerTest {

    @Test
    public void generatedValueStrategy() {
        Category category = Category.of("Electronics", null, null, null);

        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Category actualCategory =
                entityManager.find(Category.class, category.getId());

        Assertions.assertNotNull(actualCategory);
    }
}
