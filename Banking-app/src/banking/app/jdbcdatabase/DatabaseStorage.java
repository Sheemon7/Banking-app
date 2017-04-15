package banking.app.jdbcdatabase;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseStorage {
    
    private static DatabaseStorage databaseStorage = null;
    private Connection conn;
    
    private DatabaseStorage() throws ClassNotFoundException, SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        conn = databaseConnection.getConnection();
    }
    
    public static DatabaseStorage getInstance() throws ClassNotFoundException, SQLException {
        if (databaseStorage == null) {
            databaseStorage = new DatabaseStorage();
        }
        return databaseStorage;        
    }
    
}
