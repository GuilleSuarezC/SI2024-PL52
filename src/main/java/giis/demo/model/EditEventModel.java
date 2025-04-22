package giis.demo.model;

import java.util.List;
import giis.demo.util.Database;
import giis.demo.util.Util;
import giis.demo.util.ApplicationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class EditEventModel {
	private Database db = new Database();
    
	//Consultas SQL
	private static final String SQL_GET_EVENTS = "SELECT event_id, event_name, event_edition, event_date, event_endDate, event_status FROM Event WHERE event_date BETWEEN ? AND ?";
	
	private static final String SQL_GET_SPONSORSHIP_LEVEL = "SELECT level_id, level_name, level_price FROM SponsorshipLevel WHERE event_id = ?";
	
	private static final String SQL_INSERT_SPONSORSHIP_LEVEL = "INSERT INTO SponsorshipLevel (level_name, level_price, event_id) VALUES (?, ?, ?)";
	
	private static final String SQL_UPDATE_EVENT = "UPDATE Event SET event_name = ?, event_edition = ?, event_date = ?, event_endDate = ?, event_status = ? WHERE event_id = ?";


	public List<EditEventDTO> getEvents(String startDate, String endDate) {
    	return db.executeQueryPojo(EditEventDTO.class, SQL_GET_EVENTS, startDate, endDate);
    }
	
	public List<EditEventDTO> getSponsorshipLevels(int eventId) {
    	return db.executeQueryPojo(EditEventDTO.class, SQL_GET_SPONSORSHIP_LEVEL, eventId);
    }
	
	public void RegisterSponsorshipLevel(String name, double amount, int eventID)
	{
		validateNotNull(amount, "The amount cannot be null");
		validateNotNull(name, "The name cannot be null");
		
		db.executeUpdate(SQL_INSERT_SPONSORSHIP_LEVEL, name, amount, eventID);		
	}
	
	public void UpdateEvent(String name, String edition, String date, String endDate, String status, int eventId)
	{
		validateNotNull(name, "The name cannot be null");
		validateNotNull(edition, "The edition cannot be null");
		validateNotNull(date, "The start date cannot be null");
		validateNotNull(endDate, "The end date cannot be null");
		validateNotNull(status, "The status cannot be null");
		validateNotNull(eventId, "The event id cannot be null");
		db.executeUpdate(SQL_UPDATE_EVENT, name, edition, date, endDate, status, eventId);		
	}
	
    private void validateNotNull(Object obj, String message) {
    	if (obj== null || obj.toString().trim().isEmpty()) {
    		throw new ApplicationException(message);
    	}
    }
}