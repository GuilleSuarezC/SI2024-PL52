package giis.demo.model;

import java.util.Date;


public class PendingPaymentDTO{
	
	private int sponsorship_id;
	private String sponsorship_name;
    private String sponsorship_agreementDate;
    private double amount;
    private String event_name;
    private String invoice_date;
    private int invoice_id;

    // Constructor
    public PendingPaymentDTO(int sponsorship_id, String sponsorship_name, String sponsorship_agreementDate, double amount, String event_name, String invoice_date, int invoice_id) {
    	this.sponsorship_id = sponsorship_id;
        this.sponsorship_name = sponsorship_name;
        this.sponsorship_agreementDate = sponsorship_agreementDate;
        this.amount = amount;
        this.event_name = event_name;
        this.invoice_date = invoice_date;
        this.invoice_id = invoice_id;
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

    public int getInvoice_id() { 
    	return invoice_id; 
    }
    public void setInvoice_id(int invoice_id) { 
    	this.invoice_id = invoice_id; 
    }
    
    public int getSponsorship_id() { 
    	return sponsorship_id; 
    }
    public void setSponsorship_id(int sponsorship_id) { 
    	this.sponsorship_id = sponsorship_id; 
    }
    
    @Override
    public String toString() {
        return "PendingPaymentDTO{" +
                "sponsorshipId=" + sponsorship_id +
                ", sponsorshipName='" + sponsorship_name + '\'' +
                ", sponsorshipAgreementDate=" + sponsorship_agreementDate +
                ", amount=" + amount +
                ", eventName='" + event_name + '\'' +
                ", invoiceDate=" + invoice_date +
                ", invoiceId=" + invoice_id +
                '}';
    }


}
