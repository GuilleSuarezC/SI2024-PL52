package giis.demo.model;

public class EditEventDTO {
    private int event_id;
    private int level_id;
    private String event_name;
    private String event_date;
    private String event_endDate;
    private String event_edition;
    private String event_status;
    private String level_name;
    private double level_price;

    
    public EditEventDTO(int event_id, String event_name, String event_edition, String event_date, String event_endDate, String event_status) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_edition = event_edition;
        this.event_date = event_date;
        this.event_endDate = event_endDate;
        this.event_status = event_status;
        
    }
    
    public EditEventDTO(int level_id, String level_name, double level_price) {
    	this.setLevel_id(level_id);
    	this.level_name = level_name;
        this.level_price = level_price;
    }
    
    public EditEventDTO() {
    	
    }
    

    //Getters y Setters

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

	public String getEvent_endDate() {
		return event_endDate;
	}

	public void setEvent_endDate(String event_endDate) {
		this.event_endDate = event_endDate;
	}

	public String getLevel_name() {
		return level_name;
	}

	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}

	public double getLevel_price() {
		return level_price;
	}

	public void setLevel_price(double level_price) {
		this.level_price = level_price;
	}

	public int getLevel_id() {
		return level_id;
	}

	public void setLevel_id(int level_id) {
		this.level_id = level_id;
	}

	public String getEvent_status() {
		return event_status;
	}

	public void setEvent_status(String event_status) {
		this.event_status = event_status;
	}

}