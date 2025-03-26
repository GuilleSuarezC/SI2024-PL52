package giis.demo.model;

import java.util.Date;

public class UpdatePaymentDTO {
	private int sponsorshipId;
    private double paymentAmount;
    private Date paymentDate;

    // Constructor
    public UpdatePaymentDTO(int sponsorshipId, double paymentAmount, Date paymentDate) {
        this.sponsorshipId = sponsorshipId;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
    }

    // Getters y Setters
    public int getSponsorshipId() { 
    	return sponsorshipId; 
    }
    public void setSponsorshipId(int sponsorshipId) { 
    	this.sponsorshipId = sponsorshipId; 
    }

    public double getPaymentAmount() { return paymentAmount; }
    public void setPaymentAmount(double paymentAmount) { 
    	this.paymentAmount = paymentAmount; 
    }

    public Date getPaymentDate() { 
    	return paymentDate; 
    }
    public void setPaymentDate(Date paymentDate) { 
    	this.paymentDate = paymentDate; 
    }
}
