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

public class CancelIncomeExpenseModel {
	private Database db = new Database();
    
	//Consultas SQL
	private static final String SQL_GET_AGREEMENTS = "SELECT s.sponsorship_id, b.balance_id, e.event_id, s.sponsorship_name, s.sponsorship_agreementDate, b.amount, b.balance_status, e.event_name, e.event_date, e.event_endDate, e.event_status FROM Sponsorship s LEFT JOIN Balance b ON s.balance_id = b.balance_id LEFT JOIN Event e ON s.event_id = e.event_id WHERE s.sponsorship_id IS NOT NULL AND e.event_date BETWEEN ? AND ?";
	
	private static final String SQL_GET_INCOME = "SELECT b.balance_id, b.concept, b.amount, b.balance_status, e.event_name, e.event_date, e.event_endDate, e.event_status FROM Balance b LEFT JOIN Sponsorship s ON b.balance_id = s.balance_id LEFT JOIN Event e ON b.event_id = e.event_id WHERE s.sponsorship_id IS NULL AND b.amount > 0 AND e.event_date BETWEEN ? AND ?";
	
	private static final String SQL_GET_EXPENSES = "SELECT b.balance_id, b.concept, b.amount, b.balance_status, e.event_name, e.event_date, e.event_endDate, e.event_status FROM Balance b LEFT JOIN Sponsorship s ON b.balance_id = s.balance_id LEFT JOIN Event e ON b.event_id = e.event_id WHERE s.sponsorship_id IS NULL AND b.amount < 0 AND e.event_date BETWEEN ? AND ?";
	
	private static final String SQL_GET_MOVEMENTS = "SELECT m.movement_amount, m.movement_date FROM Movement m WHERE m.balance_id = ?";
	
	private static final String SQL_CANCEL = "UPDATE Balance SET balance_status = 'Cancelled' WHERE balance_id = ?";
	
	private static final String SQL_REGISTER_MOVEMENT = "INSERT INTO Movement (movement_amount, movement_date, balance_id) VALUES (?, ?, ?)";
	
	private static final String SQL_UPDATE_EVENT_STATUS = "UPDATE Event SET event_status = CASE WHEN event_date > ? THEN 'Planned' WHEN event_date <= ? AND event_endDate >= ? THEN 'Ongoing' WHEN event_endDate < ? THEN 'Completed' END WHERE event_date IS NOT NULL AND event_endDate IS NOT NULL AND event_status != 'Closed'";
	

	public List<AgreementIncomeExpenseDTO> getAgreements(String startDate, String endDate) {
    	return db.executeQueryPojo(AgreementIncomeExpenseDTO.class, SQL_GET_AGREEMENTS, startDate, endDate);
    }
	
	public List<AgreementIncomeExpenseDTO> getIncome(String startDate, String endDate) {
    	return db.executeQueryPojo(AgreementIncomeExpenseDTO.class, SQL_GET_INCOME, startDate, endDate);
    }
	
	public List<AgreementIncomeExpenseDTO> getExpenses(String startDate, String endDate) {
    	return db.executeQueryPojo(AgreementIncomeExpenseDTO.class, SQL_GET_EXPENSES, startDate, endDate);
    }
	
	public List<MovementDTO> getMovements(int balanceId) {
    	return db.executeQueryPojo(MovementDTO.class, SQL_GET_MOVEMENTS, balanceId);
    }
	
	public void RegisterPayment(double amount, String paymentDate, int balanceID)
	{
		validateNotNull(amount, "The amount cannot be null");
		validateNotNull(paymentDate, "The date of payment cannot be null");
		
		db.executeUpdate(SQL_REGISTER_MOVEMENT, amount, paymentDate, balanceID);		
	}
	
	public void CancelBalance(int balanceId)
	{
		validateNotNull(balanceId, "The balance id cannot be null");
		db.executeUpdate(SQL_CANCEL, balanceId);		
	}
	
	public void updateEventStatuses(String referenceDate) {
		validateNotNull(referenceDate, "The date cannot be null");
	    db.executeUpdate(SQL_UPDATE_EVENT_STATUS, referenceDate, referenceDate, referenceDate, referenceDate);
	}
	
    private void validateNotNull(Object obj, String message) {
    	if (obj== null || obj.toString().trim().isEmpty()) {
    		throw new ApplicationException(message);
    	}
    }
}