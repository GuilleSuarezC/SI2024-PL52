package giis.demo.model;

import java.util.List;
import giis.demo.util.Database;

public class CloseEventModel {
    private Database db = new Database();

    // Query para obtener todos los eventos (excluyendo los ya cerrados)
    private static final String SQL_GET_ALL_EVENTS = 
    	    "SELECT event_id, event_name, event_edition, event_date, event_endDate, event_status " +
    	    "FROM Event " +
    	    "WHERE event_status != 'Closed'";



    // Query para cerrar un evento
    private static final String SQL_CLOSE_EVENT = 
        "UPDATE Event SET event_status = 'Closed' WHERE event_id = ?";

    // Query para verificar si todos los balances están pagados
    private static final String SQL_IS_EVENT_PAID = 
    	    "SELECT CASE WHEN NOT EXISTS (" +
    	    "   SELECT 1 FROM Balance b " +
    	    "   LEFT JOIN (" +
    	    "       SELECT balance_id, SUM(movement_amount) AS total_paid " +
    	    "       FROM Movement GROUP BY balance_id" +
    	    "   ) m ON b.balance_id = m.balance_id " +
    	    "   WHERE b.event_id = ? " +
    	    "   AND (" +
    	    "       (b.amount > 0 AND COALESCE(m.total_paid, 0) < b.amount) OR " +
    	    "       (b.amount < 0 AND COALESCE(m.total_paid, 0) > b.amount)" +
    	    ")) THEN 1 ELSE 0 END AS all_paid";
    
 // New query to update event status
    private static final String SQL_UPDATE_EVENT_STATUS = 
        "UPDATE Event SET event_status = ? WHERE event_id = ?";
    
    /**
     * Obtiene todos los eventos no cerrados.
     */
    public List<ConsultEventDTO> getEvents() {
        return db.executeQueryPojo(ConsultEventDTO.class, SQL_GET_ALL_EVENTS);
    }

    /**
     * Cierra un evento (cambia su estado a "Closed").
     */
    public void closeEvent(int eventId) {
        db.executeUpdate(SQL_CLOSE_EVENT, eventId);
    }

    /**
     * Verifica si todos los balances del evento están pagados.
     * @return true si todos están pagados, false si hay al menos uno pendiente.
     */
    public boolean isEventPaid(int eventId) {
        List<PaymentStatusDTO> result = db.executeQueryPojo(
            PaymentStatusDTO.class, 
            SQL_IS_EVENT_PAID, 
            eventId
        );
        return !result.isEmpty() && result.get(0).isAllPaid();
    }
    
 // New method to update event status
    public void updateEventStatus(int eventId, String newStatus) {
        db.executeUpdate(SQL_UPDATE_EVENT_STATUS, newStatus, eventId);
    }
}