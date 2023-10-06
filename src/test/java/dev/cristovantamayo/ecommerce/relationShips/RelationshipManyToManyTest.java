package dev.cristovantamayo.ecommerce.relationShips;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class RelationshipManyToManyTest extends EntityManagerTest {

    @Test
    public void relationshipValidationFailing() {
        Product product = entityManager.find(Product.class, 1);
        Category category = entityManager.find(Category.class, 1);

        Assert.assertTrue(category.getProducts().isEmpty());

        entityManager.getTransaction().begin();
        // failing persistence, category is not the owner
        category.setProducts(Arrays.asList(product));
        entityManager.getTransaction().commit();

        entityManager.clear();

        Category actualCategory = entityManager.find(Category.class, category.getId());

        Assert.assertTrue(actualCategory.getProducts().isEmpty());
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
        Assert.assertFalse(actualCategory.getProducts().isEmpty());
    }


}
