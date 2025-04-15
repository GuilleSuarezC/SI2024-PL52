package giis.demo.model;

public class LTASPLevelDTO {
    private int level_id;
    private String level_name;
    private double level_price;
    private int event_id;

    // Constructor vacío (necesario para DbUtils)
    public LTASPLevelDTO() {}

    // Getters y Setters (nombres exactos a las columnas SQL)
    public int getLevel_id() { return level_id; }
    public void setLevel_id(int level_id) { this.level_id = level_id; }

    public String getLevel_name() { return level_name; }
    public void setLevel_name(String level_name) { this.level_name = level_name; }

    public double getLevel_price() { return level_price; }
    public void setLevel_price(double level_price) { this.level_price = level_price; }

    public int getEvent_id() { return event_id; }
    public void setEvent_id(int event_id) { this.event_id = event_id; }

    // Formato para mostrar en el ComboBox
    @Override
    public String toString() {
        return level_name + " - " + String.format("%.2f€", level_price);
    }
}