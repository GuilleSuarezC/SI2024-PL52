package giis.demo.model;

import giis.demo.util.Database;
import java.util.List;

public class LTAModel {
    private Database db = new Database();
    
    private static final String SQL_GET_COMPANIES = 
        "SELECT company_id, company_name FROM Company";
    
    private static final String SQL_GET_ACTIVE_EVENTS = 
    	    "SELECT event_id, event_name, event_edition, " +
    	    "strftime('%Y-%m-%d', event_date) AS event_date, " + // Convertir DATE a String
    	    "strftime('%Y-%m-%d', event_endDate) AS event_endDate, " +
    	    "event_status " +
    	    "FROM Event " +
    	    "WHERE event_status NOT IN ('Closed') " +
    	    "ORDER BY event_date DESC";

    private static final String SQL_GET_SPONSORSHIP_LEVELS = 
            "SELECT level_id, level_name, level_price, event_id " + // Incluir event_id si es necesario
            "FROM SponsorshipLevel " +
            "WHERE event_id = ?";

    private static final String SQL_INSERT_BALANCE = 
    	    "INSERT INTO Balance (concept, event_id, amount, balance_status) VALUES (?, ?, ?, 'Estimated')";
    
    public int createLongTermAgreement(String startDate, String endDate, double totalFee, int companyId) {
        String sql = "INSERT INTO LongTermAgreement (lta_startDate, lta_endDate, lta_totalFee, company_id) VALUES (?, ?, ?, ?)";
        return db.executeUpdateAndGetKey(sql, startDate, endDate, totalFee, companyId);
    }
    
    public int createBalanceEntry(String concept, int eventId, double amount) {
        String sql = "INSERT INTO Balance (concept, event_id, amount, balance_status) VALUES (?, ?, ?, 'Estimated')";
        return db.executeUpdateAndGetKey(sql, concept, eventId, amount);
    }
    
    public void linkLTAEvent(int ltaId, int eventId, int balanceId) {
        String sql = "INSERT INTO LTA_Event (lta_id, event_id, balance_id) VALUES (?, ?, ?)";
        db.executeUpdate(sql, ltaId, eventId, balanceId);
    }
    
    public List<LTASPLevelDTO> getSponsorshipLevels(int eventId) {
        return db.executeQueryPojo(LTASPLevelDTO.class, SQL_GET_SPONSORSHIP_LEVELS, eventId);
    }
    
    public List<LTAEventDTO> getActiveEvents() {
        return db.executeQueryPojo(LTAEventDTO.class, SQL_GET_ACTIVE_EVENTS);
    }
    
    public List<CompanyDTO> getCompanies() {
        return db.executeQueryPojo(CompanyDTO.class, SQL_GET_COMPANIES);
    }
}
