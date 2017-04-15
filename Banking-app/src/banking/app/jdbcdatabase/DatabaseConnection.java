package banking.app.jdbcdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    
  private static final String CONNECTION = "jdbc:postgresql://slon.felk.cvut.cz/student_db17_23";
  private static final String USERNAME = "student_db17_23";
  private static final String PASSWORD = "shimando";
  
  private final Connection conn;
  
  public DatabaseConnection() throws ClassNotFoundException, SQLException {
      /** Creates a new instance of DatabaseConnection */
      Class.forName("org.postgresql.Driver");
      this.conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
  }
  
  public Connection getConnection() {
      return this.conn;
  }
  
}