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
	private static final String SQL_GET_EVENTS = "SELECT event_id, event_name, event_edition, event_date, event_endDate, event_status FROM Event WHERE event_status != 'Closed' AND event_date BETWEEN ? AND ?";
	
	private static final String SQL_GET_EVENTS_STATUS = "SELECT event_id, event_name, event_edition, event_date, event_endDate, event_status FROM Event WHERE event_status = ? AND event_date BETWEEN ? AND ?";
	
	private static final String SQL_GET_SPONSORSHIP_LEVEL = "SELECT level_id, level_name, level_price FROM SponsorshipLevel WHERE event_id = ?";
	
	private static final String SQL_INSERT_SPONSORSHIP_LEVEL = "INSERT INTO SponsorshipLevel (level_name, level_price, event_id) VALUES (?, ?, ?)";
	
	private static final String SQL_UPDATE_EVENT_CANCELLED = "UPDATE Event SET event_name = ?, event_edition = ?, event_date = ?, event_endDate = ?, event_status = ? WHERE event_id = ?";

	private static final String SQL_UPDATE_EVENT = "UPDATE Event SET event_name=?, event_edition=?, event_date=?, event_endDate=?, event_status= CASE WHEN ? < ? THEN 'Planned' WHEN ? >= ? AND ? <= ? THEN 'Ongoing' WHEN ? > ? THEN 'Completed' END WHERE event_id=?";
	
	private static final String SQL_UPDATE_EVENT_STATUS = "UPDATE Event SET event_status = CASE WHEN event_date > ? THEN 'Planned' WHEN event_date <= ? AND event_endDate >= ? THEN 'Ongoing' WHEN event_endDate < ? THEN 'Completed' END WHERE event_date IS NOT NULL AND event_endDate IS NOT NULL AND event_status != 'Closed'";	

	public List<EditEventDTO> getEvents(String startDate, String endDate, String status) {
		
		if (status == "All")
		{
			return db.executeQueryPojo(EditEventDTO.class, SQL_GET_EVENTS, startDate, endDate);
		}else {
			return db.executeQueryPojo(EditEventDTO.class, SQL_GET_EVENTS_STATUS, status, startDate, endDate);
		}
		
    	
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
	
	public void UpdateEvent(String name, String edition, String date, String endDate, String today, int eventId)
	{
		validateNotNull(name, "The name cannot be null");
		validateNotNull(edition, "The edition cannot be null");
		validateNotNull(date, "The start date cannot be null");
		validateNotNull(endDate, "The end date cannot be null");		
		validateNotNull(eventId, "The event id cannot be null");
		if(today.equals("Closed") ) {
			db.executeUpdate(SQL_UPDATE_EVENT_CANCELLED, name, edition, date, endDate, "Closed", eventId);
		}else {
			db.executeUpdate(SQL_UPDATE_EVENT, name, edition, date, endDate, today, date, today, date, today, endDate, today, endDate, eventId);
		}
				
	}
	
	public void updateEventStatuses(String referenceDate) {
		validateNotNull(referenceDate, "The date cannot be null");
	    db.executeUpdate(SQL_UPDATE_EVENT_STATUS, referenceDate, referenceDate, referenceDate, referenceDate);
	}
	
    private void validateNotNull(Object obj, String message) {
    	if (obj== null || obj.toString().trim().isEmpty()) {
    		throw new ApplicationException(message);
    	}
    }
}