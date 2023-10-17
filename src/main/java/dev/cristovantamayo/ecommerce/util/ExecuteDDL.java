package dev.cristovantamayo.ecommerce.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class ExecuteDDL {
    public static void main(String[] args) {
        Map<String, String> properties = new HashMap<>();

        properties.put("jakarta.persistence.schema-generation.database.action",
                "drop-and-create");

        properties.put("jakarta.persistence.schema-generation.create-source",
                "metadata-then-script");
        properties.put("jakarta.persistence.schema-generation.drop-source",
                "metadata-then-script");

        properties.put("jakarta.persistence.schema-generation.create-script-source",
                "META-INF/database/script-to-create.sql");
        properties.put("jakarta.persistence.schema-generation.drop-script-source",
                "META-INF/database/script-to-drop.sql");

        properties.put("jakarta.persistence.sql-load-script-source",
                "META-INF/database/initials-inserts.sql");

        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU", properties);

        entityManagerFactory.close();
    }
}
