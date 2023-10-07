package dev.cristovantamayo.ecommerce.relationShips;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Category;
import dev.cristovantamayo.ecommerce.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RelationshipManyToManyTest extends EntityManagerTest {

    @Test
    public void relationshipValidationFailing() {
        Product product = entityManager.find(Product.class, 1);
        Category category = entityManager.find(Category.class, 1);

        Assertions.assertTrue(category.getProducts().isEmpty());

        entityManager.getTransaction().begin();
        // failing persistence, category is not the owner
        category.setProducts(Arrays.asList(product));
        entityManager.getTransaction().commit();

        entityManager.clear();

        Category actualCategory = entityManager.find(Category.class, category.getId());

        Assertions.assertTrue(actualCategory.getProducts().isEmpty());
    }

    @Test
    public void relationshipValidation() {
        Product product = entityManager.find(Product.class, 1);
        Category category = entityManager.find(Category.class, 1);

        entityManager.getTransaction().begin();
        product.setCategories(Arrays.asList(category));
        entityManager.getTransaction().commit();

        entityManager.clear();

        Category actualCategory = entityManager.find(Category.class, category.getId());
        Assertions.assertFalse(actualCategory.getProducts().isEmpty());
    }


}
