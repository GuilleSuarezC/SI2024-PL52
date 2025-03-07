package giis.demo.model;

import java.util.Date;


public class RegisterPaymentsDTO{
	
	public enum Status{PAID, OVERPAID, UNDERPAID, UNPAID};
	
	private int paymentId;
    private int sponsorshipId;
    private double amount;
    private Date paymentDate;
    private int invoiceId;
    private Status status;
    
    public RegisterPaymentsDTO() {
    	
    }
    

    public RegisterPaymentsDTO(int paymentId, int sponsorshipId, double amount, Date paymentDate, int invoiceId) {
    	this.setPaymentId(paymentId);
        this.sponsorshipId = sponsorshipId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.invoiceId = invoiceId;
    }


	public int getSponsorshipId() {
        return sponsorshipId;
    }

    public void setSponsorshipId(int sponsorshipId) {
        this.sponsorshipId = sponsorshipId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }


    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public int getPaymentId() {
		return paymentId;
	}


	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}

    @Override
    public String toString() {
        return "Payment{" +
                "PaymentId=" + paymentId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", invoiceId=" + invoiceId +
                ", sponsorshipId=" + sponsorshipId +
                ", status=" + status +
                '}';
    }

}
