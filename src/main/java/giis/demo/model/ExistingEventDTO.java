package giis.demo.model;

public class ExistingEventDTO {
    private String event_name;
    private String event_edition;

    // Constructor vacío
    public ExistingEventDTO() {}

    // Getters y Setters
    public String getEvent_name() { return event_name; }
    public void setEvent_name(String event_name) { this.event_name = event_name; }

    public String getEvent_edition() { return event_edition; }
    public void setEvent_edition(String event_edition) { this.event_edition = event_edition; }
}