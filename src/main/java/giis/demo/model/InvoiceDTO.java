package giis.demo.model;

import java.util.Date;

public class InvoiceDTO {

    private int invoiceId;
    private String taxDataName;
    private Date invoiceDate;
    private boolean invoiceAdvance;
    private String invoiceNumber;  
    private int sponsorshipId;

    public InvoiceDTO(int invoiceId, String taxDataName, Date invoiceDate, boolean invoiceAdvance, String invoiceNumber, int sponsorshipId) {
        this.invoiceId = invoiceId;
        this.taxDataName = taxDataName;
        this.invoiceDate = invoiceDate;
        this.invoiceAdvance = invoiceAdvance;
        this.invoiceNumber = invoiceNumber;
        this.sponsorshipId = sponsorshipId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getTaxDataName() {
        return taxDataName;
    }

    public void setTaxDataName(String taxDataName) {
        this.taxDataName = taxDataName;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public boolean isInvoiceAdvance() {
        return invoiceAdvance;
    }

    public void setInvoiceAdvance(boolean invoiceAdvance) {
        this.invoiceAdvance = invoiceAdvance;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public int getSponsorshipId() {
        return sponsorshipId;
    }

    public void setSponsorshipId(int sponsorshipId) {
        this.sponsorshipId = sponsorshipId;
    }

    
}
