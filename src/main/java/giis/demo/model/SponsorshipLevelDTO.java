package giis.demo.model;

public class SponsorshipLevelDTO {
    
    private static int currentId = 0;  

    private int levelId;
    private int eventId;
    private String level_name;
    private double level_price;
    
    public SponsorshipLevelDTO() {
   
    }

    public SponsorshipLevelDTO(String levelName, double levelPrice) {
        this.levelId = ++currentId;  
        this.level_name = levelName;
        this.level_price = levelPrice;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    

    public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
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
}
