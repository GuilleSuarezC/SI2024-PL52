package giis.demo.model;

import java.util.Date;


public class PendingPaymentDTO{
	
	private int balance_id;
	private String sponsorship_name;
    private String sponsorship_agreementDate;
    private double amount;
    private String event_name;
    private String invoice_date;
    private String invoice_number;
    private double movement_amount;

    // Constructor
    public PendingPaymentDTO(int balance_id, String sponsorship_name, String sponsorship_agreementDate, double amount, String event_name, String invoice_date, String invoice_number, double movement_amount) {
    	this.balance_id = balance_id;
        this.sponsorship_name = sponsorship_name;
        this.sponsorship_agreementDate = sponsorship_agreementDate;
        this.amount = amount;
        this.event_name = event_name;
        this.invoice_date = invoice_date;
        this.invoice_number = invoice_number;
        this.movement_amount = movement_amount;
    }
    
    public PendingPaymentDTO() {
    }

    // Getters y Setters
    public String getSponsorship_name() {
    	return sponsorship_name; 
    }
    public void setSponsorship_Name(String sponsorship_name) { 
    	this.sponsorship_name = sponsorship_name; 
    }

    public String getSponsorship_agreementDate() { 
    	return sponsorship_agreementDate; 
    }
    public void setSponsorship_agreementDate(String sponsorship_agreementDate) { 
    	this.sponsorship_agreementDate = sponsorship_agreementDate; 
    }

    public double getAmount() { 
    	return amount; 
    }
    public void setAmount(double amount) { 
    	this.amount = amount; 
    }

    public String getEvent_name() { 
    	return event_name; 
    }
    public void setEvent_name(String event_name) { 
    	this.event_name = event_name; 
    	}

    public String getInvoice_date() { 
    	return invoice_date; 
    }
    public void setInvoice_date(String invoice_date) { 
    	this.invoice_date = invoice_date; 
    }

    public String getInvoice_number() { 
    	return invoice_number; 
    }
    public void setInvoice_number(String invoice_number) { 
    	this.invoice_number = invoice_number; 
    }
    
    public int getBalance_id() { 
    	return balance_id; 
    }
    public void setBalance_id(int balance_id) { 
    	this.balance_id = balance_id; 
    }
    
    public double getMovement_amount() {
		return movement_amount;
	}

	public void setMovement_amount(double movement_amount) {
		this.movement_amount = movement_amount;
	}

	@Override
    public String toString() {
        return "PendingPaymentDTO{" +
                "balanceId=" + balance_id +
                ", sponsorshipName='" + sponsorship_name + '\'' +
                ", sponsorshipAgreementDate=" + sponsorship_agreementDate +
                ", amount=" + amount +
                ", eventName='" + event_name + '\'' +
                ", invoiceDate=" + invoice_date +
                ", invoiceNumber=" + invoice_number +
                ", movement_amount=" + movement_amount + 
                '}';
    }


}
