package giis.demo.model;

import java.util.Date;


public class ReportDTO{
	
	private int event_id;
	private String event_name;
	private String event_edition;
	private String event_date;
	private String event_endDate;
	private String event_status;
	private double total_sponsorship_income;
	private double total_other_income;
	private double sponsorship_income_paid;
	private double other_income_paid;
	private double total_expenses;
	private double paid_expenses;
	private double estimated_balance;
	private double paid_balance;
	

    // Constructor
    public ReportDTO(int event_id,String event_name, String event_edition, String event_date, String event_endDate, String event_status, double total_sponsorship_income, double total_other_income, double sponsorship_income_paid, double other_income_paid, double total_expenses, double paid_expenses, double estimated_balance, double paid_balance) {
    	this.event_id = event_id;
    	this.event_edition = event_edition;
    	this.event_name = event_name;
    	this.event_date = event_date;
    	this.event_endDate = event_endDate;
    	this.event_status = event_status;
    	this.total_sponsorship_income = total_sponsorship_income;
    	this.total_other_income = total_other_income;
    	this.sponsorship_income_paid = sponsorship_income_paid;
    	this.other_income_paid = other_income_paid;
    	this.total_expenses = total_expenses;
    	this.paid_expenses = paid_expenses;
    	this.estimated_balance = estimated_balance;
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
	
	public String getEvent_edition() {
		return event_edition;
	}

	public void setEvent_edition(String event_edition) {
		this.event_edition = event_edition;
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

	public double getTotal_sponsorship_income() {
		return total_sponsorship_income;
	}

	public void setTotal_sponsorship_income(double total_sponsorship_income) {
		this.total_sponsorship_income = total_sponsorship_income;
	}

	public double getTotal_other_income() {
		return total_other_income;
	}

	public void setTotal_other_income(double total_other_income) {
		this.total_other_income = total_other_income;
	}

	public double getSponsorship_income_paid() {
		return sponsorship_income_paid;
	}

	public void setSponsorship_income_paid(double sponsorship_income_paid) {
		this.sponsorship_income_paid = sponsorship_income_paid;
	}

	public double getOther_income_paid() {
		return other_income_paid;
	}

	public void setOther_income_paid(double other_income_paid) {
		this.other_income_paid = other_income_paid;
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
	
	public double getEstimated_balance() {
		return estimated_balance;
	}
	
	public void setEstimated_balance(double estimated_balance) {
		this.estimated_balance = estimated_balance;
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
				", total_sponsorship_income=" + total_sponsorship_income +
				", total_other_income=" + total_other_income +
				", sponsorship_income_paid=" + sponsorship_income_paid +
				", other_income_paid=" + other_income_paid +
				", total_expenses=" + total_expenses +
				", paid_expenses=" + paid_expenses +
				", paid_balance=" + paid_balance+
				"}";
	}
	
	

}
