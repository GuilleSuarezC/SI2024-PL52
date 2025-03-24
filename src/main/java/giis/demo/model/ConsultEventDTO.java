package giis.demo.model;

public class ConsultEventDTO {
    private int event_id;
    private String event_name;
    private int event_fee;
    private String event_date;
    private String event_duration;
    private String event_edition;
    private String event_status;

    public ConsultEventDTO() {}
    
    public ConsultEventDTO(int event_id, String event_name, int event_fee, String event_date, String event_duration, String event_edition, String event_status) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_fee = event_fee;
        this.event_date = event_date;
        this.event_duration = event_duration;
        this.event_edition = event_edition;
        this.event_status = event_status;
    }

	public String getEvent_status() {
		return event_status;
	}

	public void setEvent_status(String event_status) {
		this.event_status = event_status;
	}

	public String getEvent_duration() {
		return event_duration;
	}

	public void setEvent_duration(String event_duration) {
		this.event_duration = event_duration;
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