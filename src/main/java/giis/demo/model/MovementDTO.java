package giis.demo.model;

import java.util.Date;


public class MovementDTO{
	
	private int movement_id;
    private double movement_amount;
    private String movement_date;

    // Constructor
    public MovementDTO(int movement_id, double movement_amount, String movement_date) {
    	this.movement_id = movement_id;
        this.movement_amount = movement_amount;
        this.movement_date = movement_date;
    }
    
    public MovementDTO()
    {
    	
    }
    
    // Getters y Setters

	public int getMovement_id() {
		return movement_id;
	}

	public void setMovement_id(int movement_id) {
		this.movement_id = movement_id;
	}

    public double getMovement_amount() {
		return movement_amount;
	}

	public void setMovement_amount(double movement_amount) {
		this.movement_amount = movement_amount;
	}
	
	public String getMovement_date() {
		return movement_date;
	}

	public void setMovement_date(String movement_date) {
		this.movement_date = movement_date;
	}

}
