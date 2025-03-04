package giis.demo.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import giis.demo.util.Database;

public class RegisterIncomeExpensesModel {
	
	private Database db = new Database();

	
	public void save(String concept, int eventId, double amount, String description, Date dateOfPaid) {
        String sql = "INSERT INTO Balance (concept, event_id, amount, description, dateOfPaid) VALUES (?, ?, ?, ?, ?)";
        db.executeUpdate(sql, concept, eventId, amount, description, dateOfPaid);
    }

  
	
}
