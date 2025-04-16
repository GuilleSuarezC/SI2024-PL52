package giis.demo.model;

import java.util.List;
import giis.demo.util.Database;

public class ReOpenEventModel {
    private Database db = new Database();

    private static final String SQL_GET_CLOSED_EVENTS = 
        "SELECT event_id, event_name, event_edition, event_date, event_endDate, event_status " +
        "FROM Event " +
        "WHERE event_status = 'Closed'";

    private static final String SQL_REOPEN_EVENT = 
        "UPDATE Event SET event_status = 'Planned' WHERE event_id = ?";

    public List<ConsultEventDTO> getClosedEvents() {
        return db.executeQueryPojo(ConsultEventDTO.class, SQL_GET_CLOSED_EVENTS);
    }

    public void reopenEvent(int eventId) {
        db.executeUpdate(SQL_REOPEN_EVENT, eventId);
    }

	
}
