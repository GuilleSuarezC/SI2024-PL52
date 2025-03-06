package giis.demo.model;

import java.util.List;
import giis.demo.util.Database;

/**
 * Modelo para gestionar la consulta de actividades (eventos).
 */
public class ConsultEventStatusModel {
    private Database db = new Database();

    // Consulta SQL para obtener las actividades (eventos) con los campos requeridos
    private static final String SQL_GET_EVENTS = 
        "SELECT event_id, event_name, event_edition, event_date, event_duration, event_status " +
        "FROM Event ORDER BY event_id DESC";

    // Consulta SQL para obtener los patrocinios asociados a un evento
    private static final String SQL_GET_SPONSORSHIPS_BY_EVENT_ID = 
        "SELECT s.sponsorship_name, s.sponsorship_agreementDate, p.payment_status, e.event_fee " +
        "FROM Sponsorship s " +
        "JOIN Company c ON s.company_id = c.company_id " +
        "LEFT JOIN Payment p ON s.payment_id = p.payment_id " +
        "JOIN Event e ON s.event_id = e.event_id " +
        "WHERE s.event_id = ?";
    
    private static final String SQL_GET_PAYMENTS_BY_EVENT_ID = 
    	    "SELECT p.payment_id, p.payment_amount, p.payment_date, p.payment_status " +
    	    "FROM Payment p " +
    	    "JOIN Sponsorship s ON p.payment_id = s.payment_id " +
    	    "WHERE s.event_id = ?";

    private static final String SQL_GET_BALANCE = 
    		"SELECT b.concept AS name, " +
            "       b.amount AS amount, " +
            "       CASE " +
            "           WHEN p.payment_id IS NOT NULL THEN 'Paid' " +
            "           ELSE 'Estimated' " +
            "       END AS status " +
            "FROM Balance b " +
            "LEFT JOIN Payment p ON b.balance_id = p.balance_id " +
            "WHERE b.event_id = ?";
    
    public List<IncomeEntryDTO> getBalanceByEventId(int eventId) {
        return db.executeQueryPojo(IncomeEntryDTO.class, SQL_GET_BALANCE, eventId);
    }
    
	public List<PaymentDTO> getPaymentsByEventId(int eventId) {
	    return db.executeQueryPojo(PaymentDTO.class, SQL_GET_PAYMENTS_BY_EVENT_ID, eventId);
	}

  
    public List<SponsorshipInfoDTO> getSponsorshipsByEventId(int eventId) {
        return db.executeQueryPojo(SponsorshipInfoDTO.class, SQL_GET_SPONSORSHIPS_BY_EVENT_ID, eventId);
    }
    
    /**
     * Obtiene la lista de actividades (eventos) con edición, nombre, fecha, duración y estado.
     */
    public List<ConsultEventDTO> getEvents() {
        return db.executeQueryPojo(ConsultEventDTO.class, SQL_GET_EVENTS);
    }
}

