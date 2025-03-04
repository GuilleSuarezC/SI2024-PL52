package giis.demo.model;

public class EventDTO {
    private int event_id;
    private String event_name;
    private int event_fee;
    private String event_date;

    public EventDTO() {}
    
    public EventDTO(int event_id, String event_name, int event_fee, String event_date) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_fee = event_fee;
        this.event_date = event_date;
    }

	public String getEvent_date() {
		return event_date;
	}

	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public int getEvent_fee() {
		return event_fee;
	}

	public void setEvent_fee(int event_fee) {
		this.event_fee = event_fee;
	}

//    // Getters y Setters
//    public int getEventId() { return event_id; }
//    public String getEventName() { return event_name; }
//    public int getEventFee() { return event_fee; }
}