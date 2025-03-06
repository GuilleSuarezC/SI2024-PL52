package giis.demo.model;

import java.util.Date;

public class RegisterPaymentsDTO{
    private int sponsorshipId;
    private double amount;
    private Date agreementDate;
    private int invoiceId;
    private Double amountPaid;
    private Date paymentDate;

    public RegisterPaymentsDTO(int sponsorshipId, double amount, Date agreementDate, int invoiceId) {
        this.sponsorshipId = sponsorshipId;
        this.amount = amount;
        this.agreementDate = agreementDate;
        this.invoiceId = invoiceId;
    }

    public RegisterPaymentsDTO(int invoiceId, double amountPaid, Date paymentDate) {
		// TODO Auto-generated constructor stub
    	this.invoiceId = invoiceId;
    	this.amountPaid = amountPaid;
    	this.paymentDate = paymentDate;
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

    public Date getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(Date agreementDate) {
        this.agreementDate = agreementDate;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "sponsorshipId=" + sponsorshipId +
                ", amount=" + amount +
                ", agreementDate=" + agreementDate +
                ", invoiceId=" + invoiceId +
                ", amountPaid=" + amountPaid +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
