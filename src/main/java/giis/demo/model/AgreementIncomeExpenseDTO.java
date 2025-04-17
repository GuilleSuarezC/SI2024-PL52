package giis.demo.model;

import java.util.Date;


public class AgreementIncomeExpenseDTO{
	
	private int sponsorship_id;
	private int balance_id;
	private int event_id;
	private String sponsorship_name;
	private String sponsorship_agreementDate; //REVISAR
	private String concept;
	private double amount;
	private String balance_status;
	private String event_name;
	private String event_date;
	private String event_endDate;
	private String event_status;
	
	

    // Constructor
    public AgreementIncomeExpenseDTO(int sponsorship_id, int balance_id, int event_id, String sponsorship_name, String sponsorship_agreementDate, double amount, String balance_status, String event_name, String event_date, String event_endDate, String event_status) {
    	this.sponsorship_id = sponsorship_id;
    	this.balance_id = balance_id;
    	this.event_id = event_id;
    	this.sponsorship_name = sponsorship_name;
    	this.sponsorship_agreementDate = sponsorship_agreementDate;
    	this.amount = amount;
    	this.balance_status = balance_status;
    	this.event_name = event_name;
    	this.event_date = event_date;
    	this.event_endDate = event_endDate;
    	this.event_status = event_status;
    }
    
    public AgreementIncomeExpenseDTO(int sponsorship_id, int balance_id, int event_id, String concept, double amount, String balance_status, String event_name, String event_date, String event_endDate, String event_status) {
    	this.sponsorship_id = sponsorship_id;
    	this.balance_id = balance_id;
    	this.event_id = event_id;
    	this.concept = concept;
    	this.amount = amount;
    	this.balance_status = balance_status;
    	this.event_name = event_name;
    	this.event_date = event_date;
    	this.event_endDate = event_endDate;
    	this.event_status = event_status;
    }
    
    public AgreementIncomeExpenseDTO()
    {
    	
    }

    // Getters y Setters
    

	public int getSponsorship_id() {
		return sponsorship_id;
	}

	public void setSponsorship_id(int sponsorship_id) {
		this.sponsorship_id = sponsorship_id;
	}

	public int getBalance_id() {
		return balance_id;
	}

	public void setBalance_id(int balance_id) {
		this.balance_id = balance_id;
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public String getSponsorship_name() {
		return sponsorship_name;
	}

	public void setSponsorship_name(String sponsorship_name) {
		this.sponsorship_name = sponsorship_name;
	}

	public String getSponsorship_agreementDate() {
		return sponsorship_agreementDate;
	}

	public void setSponsorship_agreementDate(String sponsorship_agreementDate) {
		this.sponsorship_agreementDate = sponsorship_agreementDate;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getBalance_status() {
		return balance_status;
	}

	public void setBalance_status(String balance_status) {
		this.balance_status = balance_status;
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
	
	

}
