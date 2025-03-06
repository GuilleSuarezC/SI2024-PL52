package giis.demo.model;

import java.util.Date;
import java.util.List;

import giis.demo.util.Database;

public class IncomeExpensesDTO {
	private int id;
    private String source;  
    private String description;
    private String dateOfPaid;
    private int eventId;
    private double amount;  
    private double paid;    
    private double estimated;
    
    
    
    
	public IncomeExpensesDTO(int id, String source, String description, String dateOfPaid, int eventId, double amount,
			double paid, double estimated) {
		super();
		this.id = id;
		this.source = source;
		this.description = description;
		this.dateOfPaid = dateOfPaid;
		this.eventId = eventId;
		this.amount = amount;
		this.paid = calculatePaid();
		this.estimated = calculateEstimated();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDateOfPaid() {
		return dateOfPaid;
	}
	public void setDateOfPaid(String dateOfPaid) {
		this.dateOfPaid = dateOfPaid;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getPaid() {
		return paid;
	}
	public void setPaid(double paid) {
		this.paid = paid;
	}
	public double getEstimated() {
		return estimated;
	}
	public void setEstimated(double estimated) {
		this.estimated = estimated;
	}  
	
	private double calculatePaid() {
	    Database db = new Database();
	    String sql = "SELECT SUM(amount) FROM Payments WHERE event_id = ?";
	    List<Object[]> results = db.executeQueryArray(sql, eventId);
	    if (!results.isEmpty() && results.get(0)[0] != null) {
	        return ((Number) results.get(0)[0]).doubleValue();
	    }
	    return 0;
	}

	private double calculateEstimated() {
	    Database db = new Database();
	    String sql = "SELECT SUM(amount) FROM Invoice WHERE event_id = ?";
	    List<Object[]> results = db.executeQueryArray(sql, eventId);
	    if (!results.isEmpty() && results.get(0)[0] != null) {
	        return ((Number) results.get(0)[0]).doubleValue();
	    }
	    return 0;
	}

	

}
