package dev.cristovantamayo.ecommerce.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class ExportDDL {

    public static void main(String[] args) {
        Map<String, String> properties = new HashMap<>();
        
        properties.put("jakarta.persistence.schema-generation.scripts.action",
                "drop-and-create");
        properties.put("jakarta.persistence.schema-generation.scripts.create-target",
                "/home/ctamayo/algaworks/workspaces/jpa/exported/script-to-create-exported.sql");
        properties.put("jakarta.persistence.schema-generation.scripts.drop-target",
                "/home/ctamayo/algaworks/workspaces/jpa/exported/script-to-drop-exported.sql");

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
