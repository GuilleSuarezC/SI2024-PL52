package giis.demo.model;

public class InvoiceDTO {
    private String sponsorName;
    private String fiscalNumber;
    private String invoiceDate;
    private String eventName;
    private double paymentAmount; 
    

    public InvoiceDTO(String sponsorName, String fiscalNumber, String invoiceDate, 
                      String eventName, double paymentAmount) {
        this.sponsorName = sponsorName;
        this.fiscalNumber = fiscalNumber;
        this.invoiceDate = invoiceDate;
        this.eventName = eventName;
        this.paymentAmount = paymentAmount;
        
    }


    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getFiscalNumber() {
        return fiscalNumber;
    }

    public void setFiscalNumber(String fiscalNumber) {
        this.fiscalNumber = fiscalNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    

   
    }

    

