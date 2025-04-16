package giis.demo.model;

public class RegisterEventDTO {
	private int event_id;
    private String event_name;
    private String event_edition;
    private String event_date;
    private String event_endDate;
    private String event_status;

    // Constructor vacío
    public RegisterEventDTO() {}

    // Getters y Setters
    public String getEvent_name() { return event_name; }
    public void setEvent_name(String event_name) { this.event_name = event_name; }

    public String getEvent_edition() { return event_edition; }
    public void setEvent_edition(String event_edition) { this.event_edition = event_edition; }

    public String getEvent_date() { return event_date; }
    public void setEvent_date(String event_date) { this.event_date = event_date; }

    public String getEvent_endDate() { return event_endDate; }
    public void setEvent_endDate(String event_endDate) { this.event_endDate = event_endDate; }

    public String getEvent_status() { return event_status; }
    public void setEvent_status(String event_status) { this.event_status = event_status; }

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

  
    
    
}