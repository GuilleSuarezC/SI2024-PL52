package giis.demo.model;

import java.util.List;
import giis.demo.util.Database;
import giis.demo.util.Util;
import giis.demo.util.ApplicationException;

/**
 * Modelo para gestionar los acuerdos de patrocinio y el balance de eventos.
 */
public class RegisterSponsorshipAgreeModel {
    private Database db = new Database();

    /**
     * Obtiene la lista de empresas disponibles para patrocinio.
     */
    public List<Object[]> getCompanies() {
        String sql = "SELECT company_id, company_name FROM Company ORDER BY company_name";
        return db.executeQueryArray(sql);
    }

    /**
     * Obtiene la lista de eventos disponibles para patrocinio.
     */
    public List<Object[]> getEvents() {
        String sql = "SELECT event_id, event_name FROM Event ORDER BY event_name";
        return db.executeQueryArray(sql);
    }

    /**
     * Obtiene la lista de miembros del consejo de administración.
     */
    public List<Object[]> getGoverningBoardMembers() {
        String sql = "SELECT DISTINCT company_member FROM Company ORDER BY company_member";
        return db.executeQueryArray(sql);
    }

    /**
     * Registra un nuevo acuerdo de patrocinio y devuelve el ID del evento.
     */
    public int registerSponsorship(String company, String event, String date, String member) {
        validateNotNull(company, "La empresa no puede ser nula");
        validateNotNull(event, "El evento no puede ser nulo");
        validateNotNull(date, "La fecha del acuerdo no puede ser nula");
        validateNotNull(member, "El miembro del consejo no puede ser nulo");

        int companyId = getCompanyIdByName(company);
        int eventId = getEventIdByName(event);
        String formattedDate = Util.dateToIsoString(Util.isoStringToDate(date));

        // Insertar en Sponsorship
        String sqlSponsorship = "INSERT INTO Sponsorship (sponsorship_name, sponsorship_agreementDate, company_id, event_id, payment_id, invoice_id) "
                              + "VALUES (?, ?, ?, ?, 0, 0)";
        db.executeUpdate(sqlSponsorship, company + " - " + event, formattedDate, companyId, eventId);

        return eventId; // Devuelve el ID del evento para registrar balance
    }

    /**
     * Registra un ingreso o gasto en el balance de un evento.
     */
    public void registerBalance(int eventId, String source, int estimatedIncome, int paidIncome, int estimatedExpenses, int paidExpenses) {
        validateNotNull(source, "La fuente no puede ser nula");

        String sql = "INSERT INTO Balance (event_id, source, estimated_income, paid_income, estimated_expenses, paid_expenses) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        db.executeUpdate(sql, eventId, source, estimatedIncome, paidIncome, estimatedExpenses, paidExpenses);
    }

    /**
     * Obtiene la lista de acuerdos de patrocinio registrados.
     */
    public List<Object[]> getSponsorships() {
        String sql = "SELECT sponsorship_id, sponsorship_name, sponsorship_agreementDate FROM Sponsorship ORDER BY sponsorship_agreementDate DESC";
        return db.executeQueryArray(sql);
    }

    /**
     * Obtiene los detalles de un acuerdo de patrocinio por su ID.
     */
    public String[] getSponsorshipDetails(int sponsorshipId) {
        String sql = "SELECT sponsorship_id, sponsorship_name, sponsorship_agreementDate FROM Sponsorship WHERE sponsorship_id = ?";
        List<String[]> result = db.executeQueryPojo(String[].class, sql, sponsorshipId);
        if (result.isEmpty()) {
            throw new ApplicationException("No se encontró el acuerdo con ID: " + sponsorshipId);
        }
        return result.get(0);
    }

    /**
     * Obtiene el ID de una empresa a partir de su nombre.
     */
    private int getCompanyIdByName(String name) {
        String sql = "SELECT company_id FROM Company WHERE company_name = ?";
        List<Object[]> result = db.executeQueryArray(sql, name);
        if (result.isEmpty()) {
            throw new ApplicationException("No se encontró la empresa: " + name);
        }
        return (int) result.get(0)[0];
    }

    /**
     * Obtiene el ID de un evento a partir de su nombre.
     */
    private int getEventIdByName(String name) {
        String sql = "SELECT event_id FROM Event WHERE event_name = ?";
        List<Object[]> result = db.executeQueryArray(sql, name);
        if (result.isEmpty()) {
            throw new ApplicationException("No se encontró el evento: " + name);
        }
        return (int) result.get(0)[0];
    }

    /**
     * Obtiene los balances de un evento específico.
     */
    public List<Object[]> getEventBalance(int eventId) {
        String sql = "SELECT balance_id, source, estimated_income, paid_income, estimated_expenses, paid_expenses "
                   + "FROM Balance WHERE event_id = ? ORDER BY balance_id";
        return db.executeQueryArray(sql, eventId);
    }

    /**
     * Método auxiliar para validar que un valor no sea nulo o vacío.
     */
    private void validateNotNull(Object obj, String message) {
        if (obj == null || obj.toString().trim().isEmpty()) {
            throw new ApplicationException(message);
        }
    }
}
