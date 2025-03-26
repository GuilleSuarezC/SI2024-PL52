package giis.demo.model;

import java.util.Date;


public class ReportDTO{
	
	private String event_name;
	private String event_date;
	private String event_endDate;
	private String event_status;
	private double amount;
	private double movement_amount;
	

    // Constructor
    public ReportDTO(String event_name, String event_date, String event_endDate, String event_status, double amount, double movement_amount) {
    	this.event_name = event_name;
    	this.event_date = event_date;
    	this.event_endDate = event_endDate;
    	this.event_status = event_status;
    	this.amount = amount;
    	this.movement_amount = movement_amount;
    }
    
    public ReportDTO() {
    }

    // Getters y Setters
    
    public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public String getEvent_date() {
		return event_date;
	}

	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}

	public String getEvent_endDate() {
		return event_endDate;
	}

	public void setEvent_endDate(String event_endDate) {
		this.event_endDate = event_endDate;
	}

	public String getEvent_status() {
		return event_status;
	}

	public void setEvent_status(String event_status) {
		this.event_status = event_status;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getMovement_amount() {
		return movement_amount;
	}

	public void setMovement_amount(double movement_amount) {
		this.movement_amount = movement_amount;
	}

	@Override
	public String toString() {
		return "ReportDTO {"
				+"event_name=" + event_name + 
				", event_date=" + event_date +
				", event_endDate=" + event_endDate + 
				", event_status=" + event_status +
				", amount=" + amount +
				", movement_amount=" + movement_amount+
				"}";
	}
	
	

}
