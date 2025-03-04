package giis.demo.model;

public class BalanceDTO {
    private int event_id;
    private String source;
    private int amount;

    public BalanceDTO() {
    	
    }
    
    public BalanceDTO(int event_id, String source, int amount) {
        this.event_id = event_id;
        this.source = source;
        this.amount = amount;
    }

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

//    // Getters y Setters
//    public int getEventId() { return event_id; }
//    public String getSource() { return source; }
//    public int getAmount() { return amount;}
}
