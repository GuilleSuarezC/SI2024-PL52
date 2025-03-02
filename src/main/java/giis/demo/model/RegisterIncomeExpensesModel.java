package giis.demo.model;
import java.util.Date;
import java.util.List;

import giis.demo.util.Database;

public class RegisterIncomeExpensesModel {

	private int id;
	private String concept;
	private String description;
	private double qestimated;
	private double qpaid;
	private Date dateOfPaid;
	private Database db = new Database();

	
	public RegisterIncomeExpensesModel(int id, String concept, String description, double qestimated,
			double qpaid, Date dateOfPaid) {
		super();
		this.id = id;
		this.concept = concept;
		this.description = description;
		this.qestimated = qestimated;
		this.qpaid = qpaid;
		this.dateOfPaid = dateOfPaid;
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
	public double getQestimated() {
		return qestimated;
	}
	public void setQestimated(double qestimated) {
		this.qestimated = qestimated;
	}
	public double getQpaid() {
		return qpaid;
	}
	public void setQpaid(double qpaid) {
		this.qpaid = qpaid;
	}
	public Date getDateOfPaid() {
		return dateOfPaid;
	}
	public void setDateOfPaid(Date dateOfPaid) {
		this.dateOfPaid = dateOfPaid;
	}
	
	
	public void save() {
        String sql = "INSERT INTO Balance (concept, description, qestimated, qpaid, dateOfPaid) VALUES (?, ?, ?, ?, ?)";
        db.executeUpdate(sql, concept, description, qestimated, qpaid, dateOfPaid);
    }

   
    public List<Object[]> getAllMovements() {
        String sql = "SELECT * FROM Balance";
        return db.executeQueryArray(sql);
    }
	
	
}
