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

public class RegisterPaymentsModel {
	private Database db = new Database();
    
	//Consultas SQL
	private static final String SQL_GET_PENDING_PAYMENTS = "SELECT b.balance_id, s.sponsorship_name, s.sponsorship_agreementDate, b.amount, e.event_name, i.invoice_date, i.invoice_id FROM Sponsorship s LEFT JOIN Balance b ON s.balance_id = b.balance_id LEFT JOIN Event e ON s.event_id = e.event_id LEFT JOIN Invoice i ON s.sponsorship_id = i.sponsorship_id WHERE b.balance_status != 'Paid'";
	
	private static final String SQL_INSERT_PAYMENTS = "INSERT INTO Movement (movement_amount, movement_date, balance_id) VALUES (?, ?, ?)";
	
	private static final String SQL_UPDATE_PAYMENTS = "UPDATE Balance SET balance_status = 'Paid', dateOfPaid = ? WHERE balance_id = ?";


	public List<PendingPaymentDTO> getPendingPayments() {
    	return db.executeQueryPojo(PendingPaymentDTO.class, SQL_GET_PENDING_PAYMENTS);
    }
	
	public void RegisterPayment(double amount, Date paymentDate, int sponsorshipID)
	{
		validateNotNull(amount, "The amount cannot be null");
		validateNotNull(paymentDate, "The date of payment cannot be null");
		
		db.executeUpdate(SQL_INSERT_PAYMENTS, amount, paymentDate, sponsorshipID);		
	}
	
	public void UpdateBalance(String dateOfPaid, int balanceId)
	{
		validateNotNull(balanceId, "The balance id cannot be null");
		db.executeUpdate(SQL_UPDATE_PAYMENTS, dateOfPaid, balanceId);		
	}
	
	
	/*
	public double getBalanceAmount(int sponsorshipId) {
	    String query = "SELECT amount FROM Balance WHERE sponsorship_id = ?";

	    try {
	        List<Object[]> result = db.executeQueryArray(query, sponsorshipId);
	        if (!result.isEmpty() && result.get(0)[0] != null) {
	            return Double.parseDouble(result.get(0)[0].toString()); // Convertir el resultado a double
	        }
	    } catch (Exception e) {
	        System.out.println("Error al obtener el balance: " + e.getMessage());
	    }
	    return 0; // Retornar 0 si no se encuentra el balance
	}*/
	
    private void validateNotNull(Object obj, String message) {
    	if (obj== null || obj.toString().trim().isEmpty()) {
    		throw new ApplicationException(message);
    	}
    }
}