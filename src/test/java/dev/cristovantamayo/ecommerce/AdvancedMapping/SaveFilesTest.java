package dev.cristovantamayo.ecommerce.AdvancedMapping;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Invoice;
import dev.cristovantamayo.ecommerce.model.Product;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;

public class SaveFilesTest extends EntityManagerTest {

    @Test
    public void saveXmlInvoice() {
        Purchase purchase = entityManager.find(Purchase.class, 1);

        Invoice invoice = Invoice.of(null, purchase, loadXmlInvoiceFile(), new Date());

        entityManager.getTransaction().begin();
        entityManager.persist(invoice);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Invoice actualInvoice = entityManager.find(Invoice.class, invoice.getId());
        Assertions.assertNotNull(actualInvoice.getXml());
        Assertions.assertTrue(actualInvoice.getXml().length > 0);

        /*
        try {
            OutputStream out = new FileOutputStream(
                    Files.createFile(Paths.get(System.getProperty("user.home") + "/algaworks/actualInvoice.xml")).toFile());
              out.write(actualInvoice.getXml());
        } catch (Exception e) {
            throw new RuntimeException();
        }
        */
    }

    @Test
    public void saveProductImage() {
        Product product = entityManager.find(Product.class, 1);

        entityManager.getTransaction().begin();
        product.setPhoto(loadProducPhotoFile());
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product actualProduct = entityManager.find(Product.class, product.getId());
        Assertions.assertNotNull(actualProduct.getPhoto());
        Assertions.assertTrue(actualProduct.getPhoto().length > 0);

        /*
        try {
            OutputStream out = new FileOutputStream(
                    Files.createFile(Paths.get(System.getProperty("user.home") + "/algaworks/kindle.png")).toFile());
            out.write(actualProduct.getPhoto());
        } catch (Exception e) {
            throw new RuntimeException();
        }
        */
    }

    private byte[] loadProducPhotoFile() {
        try {
            return SaveFilesTest.class.getResourceAsStream("/kindle.png").readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static byte[] loadXmlInvoiceFile() {
        try {
            return SaveFilesTest.class.getResourceAsStream("/nota-fiscal.xml").readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
