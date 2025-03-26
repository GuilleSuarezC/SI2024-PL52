package giis.demo.model;

public class BalanceDTO {
    private int event_id;
    private String concept;
    private int amount;

    public BalanceDTO() {
    	
    }
    
    public BalanceDTO(int event_id, String concept, int amount) {
        this.event_id = event_id;
        this.concept = concept;
        this.amount = amount;
    }

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

//    // Getters y Setters
//    public int getEventId() { return event_id; }
//    public String getconcept() { return concept; }
//    public int getAmount() { return amount;}
}
