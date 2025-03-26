package giis.demo.model;

import java.util.Date;


public class ReportDTO{
	
	private int event_id;
	private String event_name;
	private String event_date;
	private String event_endDate;
	private String event_status;
	private double total_income;
	private double paid_income;
	private double total_expenses;
	private double paid_expenses;
	private double paid_balance;
	

    // Constructor
    public ReportDTO(int event_id,String event_name, String event_date, String event_endDate, String event_status, double total_income, double paid_income, double total_expenses, double paid_expenses, double paid_balance) {
    	this.event_id = event_id;
    	this.event_name = event_name;
    	this.event_date = event_date;
    	this.event_endDate = event_endDate;
    	this.event_status = event_status;
    	this.total_income = total_income;
    	this.paid_income = paid_income;
    	this.total_expenses = total_expenses;
    	this.paid_expenses = paid_expenses;
    	this.paid_balance = paid_balance;
    }
    
    public ReportDTO() {
    }

    // Getters y Setters
    
    public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}
    
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

	public double getTotal_income() {
		return total_income;
	}

	public void setTotal_income(double total_income) {
		this.total_income = total_income;
	}

	public double getPaid_income() {
		return paid_income;
	}

	public void setPaid_income(double paid_income) {
		this.paid_income = paid_income;
	}

	public double getTotal_expenses() {
		return total_expenses;
	}

	public void setTotal_expenses(double total_expenses) {
		this.total_expenses = total_expenses;
	}

	public double getPaid_expenses() {
		return paid_expenses;
	}

	public void setPaid_expenses(double paid_expenses) {
		this.paid_expenses = paid_expenses;
	}

	public double getPaid_balance() {
		return paid_balance;
	}

	public void setPaid_balance(double paid_balance) {
		this.paid_balance = paid_balance;
	}

	@Override
	public String toString() {
		return "ReportDTO {"+
				"event_id" + event_id +
				"event_name=" + event_name + 
				", event_date=" + event_date +
				", event_endDate=" + event_endDate + 
				", event_status=" + event_status +
				", total_income=" + total_income +
				", paid_income=" + paid_income +
				", total_expenses=" + total_expenses +
				", paid_expenses=" + paid_expenses +
				", paid_balance=" + paid_balance+
				"}";
	}
	
	

}
