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

