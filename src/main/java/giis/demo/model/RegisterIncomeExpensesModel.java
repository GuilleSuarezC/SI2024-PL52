package giis.demo.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import giis.demo.util.Database;

public class RegisterIncomeExpensesModel {
	
	private Database db = new Database();

    
    public void save(String source, int eventId, double amount, String description, String dateOfPaid) {
        String sql = "INSERT INTO Balance (source, event_id, amount, description, dateOfPaid) VALUES (?, ?, ?, ?, ?)";
        db.executeUpdate(sql, source, eventId, amount, description, dateOfPaid);
    }

 
    public Object[] getEventNames() {
        String sql = "SELECT event_name FROM Event";
        List<Object[]> results = db.executeQueryArray(sql);
        return results.stream().map(row -> row[0]).toArray();
    }

   
    public int getEventIdByName(String eventName) {
        String sql = "SELECT event_id FROM Event WHERE event_name = ?";
        List<Object[]> results = db.executeQueryArray(sql, eventName);
        if (!results.isEmpty()) {
            return (int) results.get(0)[0];
        }
        return -1; 
    }
  
	
}
