package banking.app.jpadatabase;

import javax.persistence.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

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
        StoredProcedureQuery storedProcedure = ENTITY_MANAGER.createStoredProcedureQuery("drop_all");
        storedProcedure.execute();
    }

    public void truncateAll() {
        StoredProcedureQuery storedProcedure = ENTITY_MANAGER.createStoredProcedureQuery("truncate_all");
        storedProcedure.execute();
    }

    public void updateBonuses(Date begin, Date end, BigDecimal multiplier) {
        StoredProcedureQuery storedProcedure = ENTITY_MANAGER.createStoredProcedureQuery("update_bonuses");
        storedProcedure.registerStoredProcedureParameter("start_date", Date.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("end_date", Date.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("multiplier", BigDecimal.class, ParameterMode.IN);
        storedProcedure.setParameter("start_date", begin);
        storedProcedure.setParameter("end_date", end);
        storedProcedure.setParameter("multiplier", multiplier);
        storedProcedure.execute();
    }

    public void updateAllProcedures() {
        TRANSACTION.begin();
        for(String fname : new String[]{"create_atm_trigger.sql", "create_iban_generation_trigger.sql",
                "drop_all.sql", "truncate_all.sql", "generate_atm.sql", "generate_card_type.sql",
                "generate_payment_place.sql", "generate_person.sql", "update_bonuses.sql"}) {
            String script = readFile(getPath("src", "main", "sql", fname),
                    Charset.defaultCharset());
            Query q = ENTITY_MANAGER.createNativeQuery(script);
            q.executeUpdate();
        }
        TRANSACTION.commit();
    }

}
