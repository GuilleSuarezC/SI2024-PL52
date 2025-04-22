package giis.demo.model;

import java.util.List;
import giis.demo.util.Database;
import giis.demo.util.Util;
import giis.demo.util.ApplicationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class EditEventModel {
	private Database db = new Database();
    
	//Consultas SQL
	private static final String SQL_GET_PENDING_PAYMENTS = "SELECT e.event_name, e.event_date, e.event_endDate, e.event_edition FROM Event e";
	
	private static final String SQL_INSERT_PAYMENTS = "INSERT INTO Movement (movement_amount, movement_date, balance_id) VALUES (?, ?, ?)";
	
	private static final String SQL_UPDATE_PAYMENTS = "UPDATE Balance SET balance_status = 'Paid' WHERE balance_id = ?";


	public List<PendingPaymentDTO> getPendingPayments() {
    	return db.executeQueryPojo(PendingPaymentDTO.class, SQL_GET_PENDING_PAYMENTS);
    }
	
	public void RegisterPayment(double amount, String paymentDate, int sponsorshipID)
	{
		validateNotNull(amount, "The amount cannot be null");
		validateNotNull(paymentDate, "The date of payment cannot be null");
		
		db.executeUpdate(SQL_INSERT_PAYMENTS, amount, paymentDate, sponsorshipID);		
	}
	
	public void UpdateBalance(int balanceId)
	{
		validateNotNull(balanceId, "The balance id cannot be null");
		db.executeUpdate(SQL_UPDATE_PAYMENTS, balanceId);		
	}
	
    private void validateNotNull(Object obj, String message) {
    	if (obj== null || obj.toString().trim().isEmpty()) {
    		throw new ApplicationException(message);
    	}
    }
}