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
	private static final String SQL_GET_PENDING_PAYMENTS = "SELECT s.sponsorship_id, s.sponsorship_name, s.sponsorship_agreementDate, b.amount, e.event_name, i.invoice_date, i.invoice_id FROM Sponsorship s LEFT JOIN Event e ON s.event_id = e.event_id LEFT JOIN Invoice i ON s.invoice_id = i.invoice_id LEFT JOIN Balance b ON e.event_id = b.event_id LEFT JOIN Payment p ON s.sponsorship_id = p.sponsorship_id WHERE b.amount < 0";
	
	private static final String SQL_INSERT_PAYMENTS = "INSERT INTO Payment (payment_amount, payment_date, sponsorship_id) VALUES (?, ?, ?)";
	
	private static final String SQL_UPDATE_PAYMENTS = "UPDATE Balance SET amount = (SELECT p.payment_amount FROM Payment p WHERE p.sponsorship_id = sponsorship_id) WHERE EXISTS (SELECT 1 FROM Payment p WHERE p.sponsorship_id = sponsorship_id);";

	
	public List<PendingPaymentDTO> getPendingPayments() {
    	return db.executeQueryPojo(PendingPaymentDTO.class, SQL_GET_PENDING_PAYMENTS);
    }
	
	public void RegisterPayment(double amount, Date paymentDate, int sponsorshipID)
	{
		validateNotNull(amount, "The amount cannot be null");
		validateNotNull(paymentDate, "The date of payment cannot be null");
		
		db.executeUpdate(SQL_INSERT_PAYMENTS, amount, paymentDate, sponsorshipID);
		db.executeUpdate(SQL_UPDATE_PAYMENTS);
	}
    
    
    private void validateNotNull(Object obj, String message) {
        if (obj == null || obj.toString().trim().isEmpty()) {
            throw new ApplicationException(message);
        }
    }
}
