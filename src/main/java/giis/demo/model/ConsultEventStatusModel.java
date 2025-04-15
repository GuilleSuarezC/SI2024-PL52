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
        "SELECT event_id, event_name, event_edition, event_date, event_endDate, event_status " +
        "FROM Event ORDER BY event_id DESC";

    // Consulta SQL para obtener los patrocinios asociados a un evento
//    private static final String SQL_GET_SPONSORSHIPS_BY_EVENT_ID = 
//        "SELECT s.sponsorship_name, s.sponsorship_agreementDate, p.balance_status, e.event_fee " +
//        "FROM Sponsorship s " +
//        "JOIN Company c ON s.company_id = c.company_id " +
//        "LEFT JOIN Balance p ON s.balance_id = p.balance_id " +
//        "JOIN Event e ON s.event_id = e.event_id " +
//        "WHERE s.event_id = ?";
    
    
//    private static final String SQL_GET_SPONSORSHIPS_BY_EVENT_ID = 
//    	    "SELECT s.sponsorship_name, s.sponsorship_agreementDate, " +
//    	    "       CASE WHEN COALESCE(SUM(m.movement_amount), 0) >= b.amount THEN 'Paid' ELSE 'Estimated' END AS payment_status, " +
//    	    "       e.event_fee " +
//    	    "FROM Sponsorship s " +
//    	    "JOIN Balance b ON s.balance_id = b.balance_id " + // Balance asociado al Sponsorship
//    	    "LEFT JOIN Movement m ON b.balance_id = m.balance_id " + // Movimientos del Balance
//    	    "JOIN Event e ON s.event_id = e.event_id " +
//    	    "WHERE s.event_id = ? " +
//    	    "  AND NOT EXISTS (SELECT 1 FROM LTA_Event le WHERE le.balance_id = b.balance_id) " +
//    	    "GROUP BY s.sponsorship_id, b.amount"; // Agrupar para sumar movimientos
    
    private static final String SQL_GET_PAYMENTS_BY_EVENT_ID = 
    	    "SELECT p.payment_id, p.payment_amount, p.payment_date, p.payment_status " +
    	    "FROM Payment p " +
    	    "JOIN Sponsorship s ON p.payment_id = s.payment_id " +
    	    "WHERE s.event_id = ?";

    private static final String SQL_GET_BALANCE = 
    	    "SELECT b.concept AS name, " +
    	    "       b.amount AS amount, " +
    	    "       CASE " +
    	    "           WHEN COALESCE(SUM(m.movement_amount), 0) >= b.amount THEN 'Paid' " +
    	    "           ELSE 'Estimated' " +
    	    "       END AS status " +
    	    "FROM Balance b " +
    	    "LEFT JOIN Movement m ON b.balance_id = m.balance_id " +
    	    "WHERE b.event_id = ? " +
    	    "GROUP BY b.balance_id";
    
// // Consulta para Sponsorships
//    private static final String SQL_GET_SPONSORSHIP_BALANCES = 
//        "SELECT b.balance_id, b.concept, b.amount " +
//        "FROM Balance b " +
//        "INNER JOIN Sponsorship s ON b.balance_id = s.balance_id " +
//        "WHERE b.event_id = ?";
//
//    // Consulta para Otras Fuentes
//    private static final String SQL_GET_OTHER_BALANCES = 
//        "SELECT balance_id, concept, amount " +
//        "FROM Balance " +
//        "WHERE event_id = ? " +
//        "AND balance_id NOT IN (SELECT balance_id FROM Sponsorship)";
    
 // Consulta para Sponsorships (incluye cálculo de estado de pago)
    private static final String SQL_GET_SPONSORSHIP_BALANCES = 
        "SELECT b.balance_id, b.concept, b.amount, " +
        "       CASE WHEN COALESCE(SUM(m.movement_amount), 0) >= b.amount THEN 'Paid' ELSE 'Estimated' END AS paymentStatus " +
        "FROM Balance b " +
        "INNER JOIN Sponsorship s ON b.balance_id = s.balance_id " +
        "LEFT JOIN Movement m ON b.balance_id = m.balance_id " +
        "WHERE b.event_id = ? " +
        "GROUP BY b.balance_id";

    // Consulta para Otras Fuentes (incluye cálculo de estado de pago)
//    private static final String SQL_GET_OTHER_BALANCES = 
//        "SELECT b.balance_id, b.concept, b.amount, " +
//        "       CASE WHEN COALESCE(SUM(m.movement_amount), 0) >= b.amount THEN 'Paid' ELSE 'Unpaid' END AS paymentStatus " +
//        "FROM Balance b " +
//        "LEFT JOIN Movement m ON b.balance_id = m.balance_id " +
//        "WHERE b.event_id = ? " +
//        "  AND b.balance_id NOT IN (SELECT balance_id FROM Sponsorship) " +
//        "GROUP BY b.balance_id";
    private static final String SQL_GET_OTHER_BALANCES = 
    	    "SELECT b.balance_id, b.concept, " +
    	    "       b.amount, " +
    	    "       CASE " +
    	    "           WHEN (b.amount > 0 AND COALESCE(SUM(m.movement_amount), 0) >= b.amount) THEN 'Paid' " +
    	    "           WHEN (b.amount < 0 AND COALESCE(SUM(m.movement_amount), 0) <= b.amount) THEN 'Paid' " +
    	    "           ELSE 'Estimated' " +
    	    "       END AS paymentStatus " +
    	    "FROM Balance b " +
    	    "LEFT JOIN Movement m ON b.balance_id = m.balance_id " +
    	    "WHERE b.event_id = ? " +
    	    "  AND b.balance_id NOT IN (SELECT balance_id FROM Sponsorship) " +
    	    "  AND b.balance_id NOT IN (SELECT balance_id FROM LTA_Event) " +
    	    "GROUP BY b.balance_id, b.concept, b.amount";
    
    
    // Consulta para Sponsorships normales
    private static final String SQL_GET_SPONSORSHIPS_BY_EVENT_ID = 
    	    "SELECT s.sponsorship_name, " +
    	    	    "       s.sponsorship_agreementDate, " +
    	    	    "       CASE WHEN COALESCE(SUM(m.movement_amount), 0) >= b.amount THEN 'Paid' ELSE 'Estimated' END AS payment_status, " +
    	    	    "       b.amount AS event_fee " + // Cambiar event_fee por b.amount
    	    	    "FROM Sponsorship s " +
    	    	    "JOIN Balance b ON s.balance_id = b.balance_id " +
    	    	    "LEFT JOIN Movement m ON b.balance_id = m.balance_id " +
    	    	    "JOIN Event e ON s.event_id = e.event_id " +
    	    	    "WHERE s.event_id = ? " +
    	    	    "GROUP BY s.sponsorship_id, s.sponsorship_name, s.sponsorship_agreementDate, b.amount, e.event_fee";

    // Consulta para LTAs
    private static final String SQL_GET_LTA_SPONSORSHIPS_BY_EVENT_ID = 
    		"SELECT b.concept AS sponsorship_name, " +
    				"       lta.lta_startDate AS sponsorship_agreementDate, " +
    				"       CASE WHEN COALESCE(SUM(m.movement_amount), 0) >= b.amount THEN 'Paid' ELSE 'Estimated' END AS payment_status, " +
    				"       b.amount AS event_fee " +
    				"FROM LongTermAgreement lta " +
    				"JOIN LTA_Event le ON lta.lta_id = le.lta_id " +
    				"JOIN Balance b ON le.balance_id = b.balance_id " +
    				"LEFT JOIN Movement m ON b.balance_id = m.balance_id " +
    				"JOIN Event e ON le.event_id = e.event_id " +
    				"WHERE le.event_id = ? " +
    				"GROUP BY lta.lta_id, lta.lta_startDate, b.concept, b.amount, e.event_fee";


    
    private static final String SQL_GET_LTA_BALANCES = 
            "SELECT b.balance_id, b.concept, b.amount, " +
            "       CASE WHEN COALESCE(SUM(m.movement_amount), 0) >= b.amount THEN 'Paid' ELSE 'Unpaid' END AS paymentStatus " +
            "FROM LTA_Event le " +
            "JOIN Balance b ON le.balance_id = b.balance_id " +
            "LEFT JOIN Movement m ON b.balance_id = m.balance_id " +
            "WHERE le.event_id = ? " +
            "GROUP BY b.balance_id, b.amount";
    
    // Consulta para LTAs (Long-Term Agreements)
//    private static final String SQL_GET_LTA_SPONSORSHIPS_BY_EVENT_ID = 
//        "SELECT s.sponsorship_name, s.sponsorship_agreementDate, " +
//        "       CASE WHEN COALESCE(SUM(m.movement_amount), 0) >= b.amount THEN 'Paid' ELSE 'Estimated' END AS payment_status, " +
//        "       b.amount AS event_fee " + // Monto positivo
//        "FROM Sponsorship s " +
//        "JOIN LTA_Event le ON s.event_id = le.event_id " +
//        "JOIN Balance b ON le.balance_id = b.balance_id " +
//        "LEFT JOIN Movement m ON b.balance_id = m.balance_id " +
//        "WHERE s.event_id = ? " +
//        "GROUP BY s.sponsorship_id, b.amount";
    
    public List<SponsorshipInfoDTO> getLTASponsorshipsByEventId(int eventId) {
        return db.executeQueryPojo(SponsorshipInfoDTO.class, SQL_GET_LTA_SPONSORSHIPS_BY_EVENT_ID, eventId);
    }
    
    public List<SponsorshipBalanceDTO> getLTABalancesByEvent(int eventId) {      
        return db.executeQueryPojo(SponsorshipBalanceDTO.class, SQL_GET_LTA_BALANCES, eventId);
    }

    
    public List<SponsorshipBalanceDTO> getSponsorshipBalances(int eventId) {
        return db.executeQueryPojo(SponsorshipBalanceDTO.class, SQL_GET_SPONSORSHIP_BALANCES, eventId);
    }

    public List<OtherBalanceDTO> getOtherBalances(int eventId) {
        return db.executeQueryPojo(OtherBalanceDTO.class, SQL_GET_OTHER_BALANCES, eventId);
    }
    
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

