package giis.demo.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import giis.demo.util.Database;

public class InvoiceSendModel {
    private Database db = new Database();
    
    

    public List<String> getEventNames() {
        String sql = "SELECT event_name FROM Event";
        List<Object[]> results = db.executeQueryArray(sql);
        return results.stream().map(row -> (String) row[0]).collect(Collectors.toList());
    }

    public List<Object[]> getSponsorsByEvent(String eventName) {

    	String sql = "SELECT c.company_name, c.company_email, i.taxData_Fnumber, CAST(b.amount AS REAL) " +
                "FROM Sponsorship s " +
                "JOIN Company c ON s.company_id = c.company_id " +
                "LEFT JOIN Invoice i ON s.sponsorship_id = i.sponsorship_id " +
                "JOIN Balance b ON s.balance_id = b.balance_id " +
                "JOIN Event ev ON s.event_id = ev.event_id " +
                "WHERE ev.event_name = ?";

        List<Object[]> results = db.executeQueryArray(sql, eventName);
        return results;
    }
    


    public boolean generateInvoice(String sponsorName, String fiscalNumber, String email, String event) {
        try {
            String invoiceDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String sponsorshipQuery = "SELECT s.sponsorship_id " +
                                      "FROM Sponsorship s " +
                                      "JOIN Company c ON s.company_id = c.company_id " +
                                      "JOIN Event ev ON s.event_id = ev.event_id " +
                                      "WHERE c.company_name = ? AND ev.event_name = ?";
            
            List<Object[]> sponsorshipResult = db.executeQueryArray(sponsorshipQuery, sponsorName, event);

            if (sponsorshipResult.isEmpty()) {
                System.out.println("Error: No se encontró un sponsorship_id para este patrocinador y evento.");
                return false;
            }

            int sponsorshipId = (int) sponsorshipResult.get(0)[0]; 

            String sql = "INSERT INTO Invoice (taxData_name, taxData_Fnumber, invoice_date, sponsorship_id) " +
                         "VALUES (?, ?, ?, ?)";
            
            db.executeUpdate(sql, sponsorName, fiscalNumber, invoiceDate, sponsorshipId);

            System.out.println("Invoice sent to: " + email);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}