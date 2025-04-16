package giis.demo.model;

import java.util.List;
import giis.demo.util.Database;
import giis.demo.util.Util;
import giis.demo.util.ApplicationException;
import giis.demo.model.CompanyDTO;
import giis.demo.model.EventDTO;
import giis.demo.model.SponsorshipDTO;
import giis.demo.model.BalanceDTO;

/**
 * Modelo para gestionar los acuerdos de patrocinio y el balance de eventos.
 * Sigue un estilo similar al de CarrerasModel, con consultas SQL parametrizadas
 * y uso de DTOs para transferir datos.
 */
public class RegisterSponsorshipAgreeModel {
    private Database db = new Database();

    // Consultas SQL
    private static final String SQL_GET_COMPANIES = "SELECT company_id, company_name FROM Company ORDER BY company_name";
    private static final String SQL_GET_EVENTS = "SELECT event_id, event_name, event_date, event_edition FROM Event ORDER BY event_name";
    private static final String SQL_GET_GOVERNING_BOARD_MEMBERS = "SELECT gb_id, gb_name, gb_rank FROM COIIPA_GBMember";
    private static final String SQL_GET_CONTACT_MEMBERS="SELECT member_id, member_name, member_email, member_phone, company_id FROM Member WHERE company_id=?";
    private static final String SQL_INSERT_SPONSORSHIP = "INSERT INTO Sponsorship (sponsorship_name, sponsorship_agreementDate, company_id, event_id, gb_id, balance_id) VALUES (?, ?, ?, ?, ?, ?)";    
    private static final String SQL_INSERT_BALANCE = "INSERT INTO Balance (event_id, concept, amount, balance_status, description) VALUES (?, ?, ?, 'Estimated', ?)";    
    private static final String SQL_GET_SPONSORSHIPS = "SELECT sponsorship_id, sponsorship_name, sponsorship_agreementDate FROM Sponsorship ORDER BY sponsorship_agreementDate DESC";
    private static final String SQL_GET_SPONSORSHIP_DETAILS = "SELECT sponsorship_id, sponsorship_name, sponsorship_agreementDate FROM Sponsorship WHERE sponsorship_id = ?";
    private static final String SQL_GET_EVENT_BALANCE = "SELECT balance_id, concept, amount FROM Balance WHERE event_id = ? ORDER BY balance_id";
    private static final String SQL_GET_SPONSORSHIP_LEVELS = "SELECT * FROM SponsorshipLevel WHERE event_id = ?";

    /**
     * Obtiene la lista de empresas disponibles para patrocinio.
     */
    public List<CompanyDTO> getCompanies() {
    	return db.executeQueryPojo(CompanyDTO.class, SQL_GET_COMPANIES);
    }

    /**
     * Obtiene la lista de eventos disponibles para patrocinio.
     */
    public List<EventDTO> getEvents() {
        return db.executeQueryPojo(EventDTO.class, SQL_GET_EVENTS);
    }

    /**
     * Obtiene la lista de miembros del consejo de administración.
     */
    public List<GBMemberDTO> getGoverningBoardMembers() {
        return db.executeQueryPojo(GBMemberDTO.class, SQL_GET_GOVERNING_BOARD_MEMBERS);
    }
    
    public List<CMemberDTO> getCompanyMembers(int id){
    	return db.executeQueryPojo(CMemberDTO.class, SQL_GET_CONTACT_MEMBERS, id);
    }

    /**
     * Registra un nuevo acuerdo de patrocinio y devuelve el ID del evento.
     */
    public int registerSponsorship(String companyName, String eventName, String eventEdition, String agreementDate, String memberName, int gbId, int balanceId) {
        validateNotNull(companyName, "Company can't be null");
        validateNotNull(eventName, "Event can't be null");
        validateNotNull(agreementDate, "Agreement date can't be null");
        validateNotNull(memberName, "GBMember can't be null");
        if(eventEdition == null) eventEdition = "";

        int companyId = getCompanyIdByName(companyName);
        int eventId = getEventIdByName(eventName);
        String formattedDate = Util.dateToIsoString(Util.isoStringToDate(agreementDate));

        // Insertar en Sponsorship
        db.executeUpdate(SQL_INSERT_SPONSORSHIP, companyName + " - " + eventName + " " + eventEdition , formattedDate, companyId, eventId, gbId, balanceId);

        return eventId; // Devuelve el ID del evento para registrar balance
    }

//    /**
//     * Registra un ingreso o gasto en el balance de un evento.
//     */
//    public void registerBalance(int eventId, String concept, int amount) {
//        validateNotNull(concept, "La fuente no puede ser nula");
//        validateNotNull(amount, "La cantidad no puede ser nula");
//
//        db.executeUpdate(SQL_INSERT_BALANCE, eventId, concept, amount);
//    }
    
   
    public int registerBalance(int eventId, String concept, double enteredFee, String description) {
        validateNotNull(description, "La descripción no puede ser nula");
        validateNotNull(enteredFee, "La cantidad no puede ser nula");
        String sql = SQL_INSERT_BALANCE + "; SELECT last_insert_rowid()"; // SQLite
        return db.executeUpdateAndGetKey(sql, eventId, concept, enteredFee, description);
    }



    /**
     * Obtiene la lista de acuerdos de patrocinio registrados.
     */
    public List<SponsorshipDTO> getSponsorships() {
        return db.executeQueryPojo(SponsorshipDTO.class, SQL_GET_SPONSORSHIPS);
    }

    /**
     * Obtiene los detalles de un acuerdo de patrocinio por su ID.
     */
    public SponsorshipDTO getSponsorshipDetails(int sponsorshipId) {
        List<SponsorshipDTO> result = db.executeQueryPojo(SponsorshipDTO.class, SQL_GET_SPONSORSHIP_DETAILS, sponsorshipId);
        validateCondition(!result.isEmpty(), "Sponsorship agreement not found with ID: " + sponsorshipId);
        return result.get(0);
    }

    /**
     * Obtiene el ID de una empresa a partir de su nombre.
     */
    private int getCompanyIdByName(String name) {
        String sql = "SELECT company_id FROM Company WHERE company_name = ?";
        List<Object[]> result = db.executeQueryArray(sql, name);
        validateCondition(!result.isEmpty(), "Company not found: " + name);
        return (int) result.get(0)[0];
    }

    /**
     * Obtiene el ID de un evento a partir de su nombre.
     */
    private int getEventIdByName(String name) {
        String sql = "SELECT event_id, event_edition FROM Event WHERE event_name = ?";
        List<Object[]> result = db.executeQueryArray(sql, name);
        validateCondition(!result.isEmpty(), "Event not found: " + name);
        return (int) result.get(0)[0];
    }

    /**
     * Obtiene los balances de un evento específico.
     */
    public List<BalanceDTO> getEventBalance(int eventId) {
        return db.executeQueryPojo(BalanceDTO.class, SQL_GET_EVENT_BALANCE, eventId);
    }

    // Métodos auxiliares para validación
    private void validateNotNull(Object obj, String message) {
        if (obj == null || obj.toString().trim().isEmpty()) {
            throw new ApplicationException(message);
        }
    }

    private void validateCondition(boolean condition, String message) {
        if (!condition) {
            throw new ApplicationException(message);
        }
    }
    
    public List<SponsorshipLevelDTO> getSponsorshipLevelsByEvent(int eventId) {
        String sql = "SELECT * FROM SponsorshipLevel WHERE event_id = ?";
        return db.executeQueryPojo(SponsorshipLevelDTO.class, sql, eventId);
    }


    public void addSponsorshipLevel(int eventId, String levelName, double levelPrice) {
        String sql = "INSERT INTO SponsorshipLevel (event_id, level_name, level_price) VALUES (?, ?, ?)";
        db.executeUpdate(sql, eventId, levelName, levelPrice);
    }



    
}