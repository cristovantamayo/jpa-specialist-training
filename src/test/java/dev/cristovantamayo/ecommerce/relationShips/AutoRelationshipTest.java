package dev.cristovantamayo.ecommerce.relationShips;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Category;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class AutoRelationshipTest extends EntityManagerTest {

    @Test
    public void relationshipValidation() {
        Category parentCategory = Category.of(null, "Home Appliances", null, null, null);
        Category category = Category.of(null, "Mixers", parentCategory, null, null);
        parentCategory.setCategories(Arrays.asList(category));

        entityManager.getTransaction().begin();
        entityManager.persist(parentCategory);
        entityManager.persist(category);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Category actualCategory = entityManager.find(Category.class, category.getId());
        Assert.assertNotNull(actualCategory.getCategories());

        Category actualParentCategory = entityManager.find(Category.class, parentCategory.getId());
        Assert.assertFalse(parentCategory.getCategories().isEmpty());

    }
}
