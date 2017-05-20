package banking.app.jpadatabase;

import javax.persistence.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabaseAccess {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("PersistenceUnit");
    static final EntityManager ENTITY_MANAGER = ENTITY_MANAGER_FACTORY.createEntityManager();
    static final EntityTransaction TRANSACTION = ENTITY_MANAGER.getTransaction();

    static String readFile(String path, Charset encoding) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, encoding);
    }

    static String getPath(String ... path) {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(currentPath.toString(), path);
        return filePath.toString();
    }

    public void dropAll() {
        StoredProcedureQuery storedProceure = ENTITY_MANAGER.createStoredProcedureQuery("drop_all");
        storedProceure.execute();
    }

    public void truncateAll() {
        StoredProcedureQuery storedProceure = ENTITY_MANAGER.createStoredProcedureQuery("truncate_all");
        storedProceure.execute();
    }

    public void updateAllProcedures() {
        TRANSACTION.begin();
        for(String fname : new String[]{"create_atm_trigger.sql", "create_iban_generation_trigger.sql",
                "drop_all.sql", "truncate_all.sql", "generate_atm.sql", "generate_card_type.sql",
                "generate_payment_place.sql", "generate_person.sql"}) {
            String script = readFile(getPath("src", "main", "sql", fname),
                    Charset.defaultCharset());
            Query q = ENTITY_MANAGER.createNativeQuery(script);
            q.executeUpdate();
        }
        TRANSACTION.commit();
    }

}
