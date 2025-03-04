package giis.demo.model;

import java.util.Date;
import java.util.List;

import giis.demo.util.Database;

public class IncomeExpenses {
	private int id;
    private String concept;  
    private String description;
    private Date dateOfPaid;
    private int eventId;
    private double amount;  
    private double paid;    
    private double estimated;
    
    
    
    
	public IncomeExpenses(int id, String concept, String description, Date dateOfPaid, int eventId, double amount,
			double paid, double estimated) {
		super();
		this.id = id;
		this.concept = concept;
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
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateOfPaid() {
		return dateOfPaid;
	}
	public void setDateOfPaid(Date dateOfPaid) {
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
