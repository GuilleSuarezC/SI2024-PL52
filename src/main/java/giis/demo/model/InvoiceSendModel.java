package giis.demo.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import giis.demo.util.Database;

public class InvoiceSendModel {
    private Database db = new Database();
    private String date;
    
    public InvoiceSendModel(String date) {
    	this.date=date;
    }
    

    public List<String> getEventNames() {
        String sql = "SELECT event_name FROM Event";
        List<Object[]> results = db.executeQueryArray(sql);
        return results.stream().map(row -> (String) row[0]).collect(Collectors.toList());
    }

    public List<Object[]> getSponsorsByEvent(String eventName) {

        String sql = "SELECT c.company_name, c.company_email, c.company_taxNumber, b.amount, c.company_address " +
                     "FROM Sponsorship s " +
                     "JOIN Company c ON s.company_id = c.company_id " +
                     "LEFT JOIN Balance b ON s.balance_id = b.balance_id " +
                     "JOIN Event ev ON s.event_id = ev.event_id " +
                     "WHERE ev.event_name = ?";


        List<Object[]> results = db.executeQueryArray(sql, eventName);
        return results;
    }

    


    public boolean generateInvoice(String sponsorName, String fiscalNumber, String address, String email, String event, boolean advance) {
        try {
            //String invoiceDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());

            String sponsorshipQuery = "SELECT s.sponsorship_id FROM Sponsorship s " +
                                      "JOIN Company c ON s.company_id = c.company_id " +
                                      "JOIN Event ev ON s.event_id = ev.event_id " +
                                      "WHERE c.company_name = ? AND ev.event_name = ?";
            
            List<Object[]> sponsorshipResult = db.executeQueryArray(sponsorshipQuery, sponsorName, event);

            if (sponsorshipResult.isEmpty()) {
                System.out.println("Error: No se encontró un sponsorship_id para este patrocinador y evento.");
                return false;
            }

            int sponsorshipId = (int) sponsorshipResult.get(0)[0]; 

            String date= this.date;
            int yearString = Integer.parseInt(date.substring(0, 4)); 
            
            
            String lastInvoiceNumberQuery = "SELECT invoice_number FROM Invoice WHERE invoice_number LIKE 'FAC" + yearString + "-%' ORDER BY invoice_id DESC LIMIT 1";
            List<Object[]> lastInvoiceResult = db.executeQueryArray(lastInvoiceNumberQuery);
            String newInvoiceNumber = generateNewInvoiceNumber(lastInvoiceResult);

            String sql = "INSERT INTO Invoice (taxData_name, invoice_date, invoice_number, sponsorship_id, invoice_advance) " +
                         "VALUES (?, ?, ?, ?, ?)";
            db.executeUpdate(sql, sponsorName, date, newInvoiceNumber, sponsorshipId, advance);

            System.out.println("Invoice Number: " + newInvoiceNumber);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateNewInvoiceNumber(List<Object[]> lastInvoiceResult) {
    	String date= this.date;
        int yearString = Integer.parseInt(date.substring(0, 4)); 
       
        int nextNumber = 1;

        if (!lastInvoiceResult.isEmpty()) {
            String lastInvoiceNumber = (String) lastInvoiceResult.get(0)[0];
            if (lastInvoiceNumber != null && lastInvoiceNumber.startsWith("FAC" + yearString)) {
                String lastNumberPart = lastInvoiceNumber.substring(lastInvoiceNumber.lastIndexOf("-") + 1);
                try {
                    nextNumber = Integer.parseInt(lastNumberPart) + 1;
                } catch (NumberFormatException e) {
                    nextNumber = 1;
                }
            }
        }

        return "FAC" + yearString + "-" + String.format("%04d", nextNumber);
    }


}