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
    private static final String SQL_GET_EVENTS = "SELECT event_id, event_name, event_fee, event_date FROM Event ORDER BY event_name";
    private static final String SQL_GET_GOVERNING_BOARD_MEMBERS = "SELECT gb_id, gb_name FROM COIIPA_GBMember";
    private static final String SQL_GET_CONTACT_MEMBERS="SELECT member_id, member_name, member_email, member_phone, company_id FROM Member WHERE company_id=?";
    private static final String SQL_INSERT_SPONSORSHIP = "INSERT INTO Sponsorship (sponsorship_name, sponsorship_agreementDate, company_id, event_id, payment_id, invoice_id) VALUES (?, ?, ?, ?, 0, 0)";
    private static final String SQL_INSERT_BALANCE = "INSERT INTO Balance (event_id, source, amount) VALUES (?, ?, ?)";
    private static final String SQL_GET_SPONSORSHIPS = "SELECT sponsorship_id, sponsorship_name, sponsorship_agreementDate FROM Sponsorship ORDER BY sponsorship_agreementDate DESC";
    private static final String SQL_GET_SPONSORSHIP_DETAILS = "SELECT sponsorship_id, sponsorship_name, sponsorship_agreementDate FROM Sponsorship WHERE sponsorship_id = ?";
    private static final String SQL_GET_EVENT_BALANCE = "SELECT balance_id, source, amount FROM Balance WHERE event_id = ? ORDER BY balance_id";

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
    public int registerSponsorship(String companyName, String eventName, String agreementDate, String memberName) {
        validateNotNull(companyName, "La empresa no puede ser nula");
        validateNotNull(eventName, "El evento no puede ser nulo");
        validateNotNull(agreementDate, "La fecha del acuerdo no puede ser nula");
        validateNotNull(memberName, "El miembro del consejo no puede ser nulo");

        int companyId = getCompanyIdByName(companyName);
        int eventId = getEventIdByName(eventName);
        String formattedDate = Util.dateToIsoString(Util.isoStringToDate(agreementDate));

        // Insertar en Sponsorship
        db.executeUpdate(SQL_INSERT_SPONSORSHIP, companyName + " - " + eventName, formattedDate, companyId, eventId);

        return eventId; // Devuelve el ID del evento para registrar balance
    }

    /**
     * Registra un ingreso o gasto en el balance de un evento.
     */
    public void registerBalance(int eventId, String source, int amount) {
        validateNotNull(source, "La fuente no puede ser nula");
        validateNotNull(amount, "La cantidad no puede ser nula");

        db.executeUpdate(SQL_INSERT_BALANCE, eventId, source, amount);
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
        validateCondition(!result.isEmpty(), "No se encontró el acuerdo con ID: " + sponsorshipId);
        return result.get(0);
    }

    /**
     * Obtiene el ID de una empresa a partir de su nombre.
     */
    private int getCompanyIdByName(String name) {
        String sql = "SELECT company_id FROM Company WHERE company_name = ?";
        List<Object[]> result = db.executeQueryArray(sql, name);
        validateCondition(!result.isEmpty(), "No se encontró la empresa: " + name);
        return (int) result.get(0)[0];
    }

    /**
     * Obtiene el ID de un evento a partir de su nombre.
     */
    private int getEventIdByName(String name) {
        String sql = "SELECT event_id FROM Event WHERE event_name = ?";
        List<Object[]> result = db.executeQueryArray(sql, name);
        validateCondition(!result.isEmpty(), "No se encontró el evento: " + name);
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
}