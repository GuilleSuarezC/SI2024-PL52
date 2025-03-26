package giis.demo.model;

public class BalanceDTO {
    private int balanceId;
    private String concept;
    private int eventId;
    private double amount;
    private String description;
    private String dateOfPaid;
    private String balanceStatus;

    public BalanceDTO(int balanceId, String concept, int eventId, double amount, String description, String dateOfPaid, String balanceStatus) {
        this.balanceId = balanceId;
        this.concept = concept;
        this.eventId = eventId;
        this.amount = amount;
        this.description = description;
        this.dateOfPaid = dateOfPaid;
        this.balanceStatus = balanceStatus;
    }

    public int getBalanceId() { return balanceId; }
    public void setBalanceId(int balanceId) { this.balanceId = balanceId; }

    public String getConcept() { return concept; }
    public void setConcept(String concept) { this.concept = concept; }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }


    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDateOfPaid() { return dateOfPaid; }
    public void setDateOfPaid(String dateOfPaid) { this.dateOfPaid = dateOfPaid; }


    public String getBalanceStatus() { return balanceStatus; }
    public void setBalanceStatus(String balanceStatus) { this.balanceStatus = balanceStatus; }



}
