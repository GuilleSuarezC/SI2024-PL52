package giis.demo.model;

public class EventDTO {
    private int event_id;
    private String event_name;
    private String event_date;
    private String event_edition;

    public EventDTO() {}
    
    public EventDTO(int event_id, String event_name, String event_date, String event_edition) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_date = event_date;
        this.event_edition = event_edition;
    }

	public String getEvent_edition() {
		return event_edition;
	}

	public void setEvent_edition(String event_edition) {
		this.event_edition = event_edition;
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

	

//    // Getters y Setters
//    public int getEventId() { return event_id; }
//    public String getEventName() { return event_name; }
//    public int getEventFee() { return event_fee; }
}